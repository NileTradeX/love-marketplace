package com.love.payment.service.impl;

import com.adyen.Client;
import com.adyen.Config;
import com.adyen.model.balanceplatform.*;
import com.adyen.model.legalentitymanagement.Address;
import com.adyen.model.legalentitymanagement.*;
import com.adyen.service.balanceplatform.AccountHoldersApi;
import com.adyen.service.balanceplatform.BalanceAccountsApi;
import com.adyen.service.legalentitymanagement.HostedOnboardingApi;
import com.adyen.service.legalentitymanagement.LegalEntitiesApi;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.util.DynamicEnumUtil;
import com.love.payment.bo.AdyenMerchantAccountCreateBO;
import com.love.payment.bo.AdyenMerchantOnboardingLinkBO;
import com.love.payment.config.AdyenConfigProperties;
import com.love.payment.dto.AdyenMerchantAccountDTO;
import com.love.payment.dto.AdyenMerchantOnboardingLinkDTO;
import com.love.payment.entity.AdyenMerchant;
import com.love.payment.enums.MerchantAccountStatus;
import com.love.payment.mapper.AdyenMerchantMapper;
import com.love.payment.service.AdyenMerchantService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdyenMerchantServiceImpl extends ServiceImpl<AdyenMerchantMapper, AdyenMerchant> implements AdyenMerchantService, InitializingBean {

    static {
        TransferInstrumentReference.openapiFields.add("trustedSource");
        JSONObject.openapiFields.add("interval");
        JSONObject.openapiFields.add("maxAmount");
        DynamicEnumUtil.addEnum(LegalEntityAssociation.TypeEnum.class, "DIRECTOR", "director");
    }

    private final Logger logger = LoggerFactory.getLogger(AdyenMerchantServiceImpl.class);

    private final Environment environment;

    private final AdyenConfigProperties adyenConfigProperties;
    private LegalEntitiesApi legalEntitiesApi;
    private AccountHoldersApi accountHoldersApi;
    private BalanceAccountsApi balanceAccountsApi;
    private HostedOnboardingApi hostedOnboardingApi;

    @Override
    public void afterPropertiesSet() {
        boolean prod = false;
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 1) {
            prod = activeProfiles[0].equalsIgnoreCase("prod");
        }

        AdyenConfigProperties.LegalEntityApi legalEntityApi = adyenConfigProperties.getLegalEntityApi();
        Config legalEntityConfig = new Config();
        legalEntityConfig.setUsername(legalEntityApi.getUsername());
        legalEntityConfig.setPassword(legalEntityApi.getPassword());
        legalEntityConfig.setEnvironment(prod ? com.adyen.enums.Environment.LIVE : com.adyen.enums.Environment.TEST);

        AdyenConfigProperties.AccountHolderApi accountHolderApi = adyenConfigProperties.getAccountHolderApi();
        Config accountHolderConfig = new Config();
        accountHolderConfig.setUsername(accountHolderApi.getUsername());
        accountHolderConfig.setPassword(accountHolderApi.getPassword());
        accountHolderConfig.setEnvironment(prod ? com.adyen.enums.Environment.LIVE : com.adyen.enums.Environment.TEST);

        legalEntitiesApi = new LegalEntitiesApi(new Client(legalEntityConfig));
        hostedOnboardingApi = new HostedOnboardingApi(new Client(legalEntityConfig));
        accountHoldersApi = new AccountHoldersApi(new Client(accountHolderConfig));
        balanceAccountsApi = new BalanceAccountsApi(new Client(accountHolderConfig));
    }

    private String defaultCurrencyCode(String country) {
        return "USD";
    }

    @Override
    public Optional<AdyenMerchant> queryByMerchantId(Long merchantId) {
        return this.lambdaQuery().eq(AdyenMerchant::getMerchantId, merchantId).oneOpt();
    }

    @Override
    public AdyenMerchantAccountDTO createAccount(AdyenMerchantAccountCreateBO merchantAccountCreateBO) {
        AdyenMerchantAccountDTO merchantAccountDTO = queryAccount(merchantAccountCreateBO.getMerchantId());
        if (StringUtils.isNotBlank(merchantAccountDTO.getBalanceAccountId())) {
            return merchantAccountDTO;
        }

        try {
            // step 1: create legal entity
            LegalEntityInfoRequiredType legalEntityInfoRequiredType = new LegalEntityInfoRequiredType();
            legalEntityInfoRequiredType.setType(LegalEntityInfoRequiredType.TypeEnum.ORGANIZATION);
            Organization organization = new Organization();
            organization.setLegalName(merchantAccountCreateBO.getLegalName());
            organization.setRegisteredAddress(new Address().country(merchantAccountCreateBO.getCountry()));
            legalEntityInfoRequiredType.setOrganization(organization);
            LegalEntity legalEntity = legalEntitiesApi.createLegalEntity(legalEntityInfoRequiredType);
            logger.error("===> legalEntity => {} : {} ", merchantAccountCreateBO.getMerchantId(), legalEntity);

            // step 2: create account holders
            AccountHolder accountHolder = accountHoldersApi.createAccountHolder(new AccountHolderInfo().legalEntityId(legalEntity.getId()));
            logger.error("===> accountHolder => {} : {} ", merchantAccountCreateBO.getMerchantId(), accountHolder);

            // step 3: create balance account
            BalanceAccount balanceAccount = balanceAccountsApi.createBalanceAccount(new BalanceAccountInfo().accountHolderId(accountHolder.getId()).defaultCurrencyCode(defaultCurrencyCode(merchantAccountCreateBO.getCountry())));
            logger.error("===> balanceAccount => {} : {} ", merchantAccountCreateBO.getMerchantId(), balanceAccount);

            AdyenMerchant adyenMerchant = new AdyenMerchant();
            adyenMerchant.setMerchantId(merchantAccountCreateBO.getMerchantId());
            adyenMerchant.setLegalEntityId(legalEntity.getId());
            adyenMerchant.setAccountHolderId(accountHolder.getId());
            adyenMerchant.setBalanceAccountId(balanceAccount.getId());
            adyenMerchant.setCreateTime(LocalDateTime.now());
            save(adyenMerchant);

            return AdyenMerchantAccountDTO.builder()
                    .merchantId(merchantAccountCreateBO.getMerchantId())
                    .balanceAccountId(balanceAccount.getId())
                    .status(MerchantAccountStatus.ONBOARDING_IN_PROCESS.getStatus())
                    .build();
        } catch (Exception exception) {
            logger.error("Create account failed!", exception);
            throw BizException.build("Create account failed!");
        }
    }

    @Override
    public AdyenMerchantAccountDTO queryAccount(Long merchantId) {
        return this.queryByMerchantId(merchantId)
                .map(adyenMerchant -> {
                    AdyenMerchantAccountDTO merchantAccountDTO = AdyenMerchantAccountDTO.builder()
                            .merchantId(merchantId)
                            .balanceAccountId(adyenMerchant.getBalanceAccountId())
                            .status(MerchantAccountStatus.COMPLETED.getStatus())
                            .build();

                    try {
                        LegalEntity legalEntity = legalEntitiesApi.getLegalEntity(adyenMerchant.getLegalEntityId());
                        Map<String, LegalEntityCapability> capabilities = legalEntity.getCapabilities();
                        for (LegalEntityCapability capability : capabilities.values()) {
                            if ("invalid".equalsIgnoreCase(capability.getVerificationStatus())) {
                                merchantAccountDTO.setStatus(MerchantAccountStatus.ONBOARDING_IN_PROCESS.getStatus());
                                break;
                            }
                        }
                    } catch (Exception exception) {
                        logger.error("Query account failed!", exception);
                        throw BizException.build("Query account failed!");
                    }
                    return merchantAccountDTO;
                })
                .orElse(AdyenMerchantAccountDTO.builder().status(MerchantAccountStatus.NEED_ONBOARDING.getStatus()).build());
    }

    @Override
    public AdyenMerchantOnboardingLinkDTO createOnboardingLink(AdyenMerchantOnboardingLinkBO merchantOnboardingLinkBO) {
        AdyenMerchant adyenMerchant = this.queryByMerchantId(merchantOnboardingLinkBO.getMerchantId()).orElseThrow(() -> BizException.build("Merchant haven't create Adyen account!"));
        try {
            OnboardingLinkInfo onboardingLinkInfo = new OnboardingLinkInfo();
            if (Objects.nonNull(merchantOnboardingLinkBO.getRedirectUrl())) {
                onboardingLinkInfo.setRedirectUrl(merchantOnboardingLinkBO.getRedirectUrl());
            }

            if (Objects.nonNull(merchantOnboardingLinkBO.getCounty())) {
                onboardingLinkInfo.setLocale(merchantOnboardingLinkBO.getCounty());
            }
            OnboardingLink onboardingLink = hostedOnboardingApi.getLinkToAdyenhostedOnboardingPage(adyenMerchant.getLegalEntityId(), onboardingLinkInfo);
            return AdyenMerchantOnboardingLinkDTO.builder().url(onboardingLink.getUrl()).build();
        } catch (Exception exception) {
            logger.error("Create onboarding link failed!", exception);
            throw BizException.build("Create onboarding link failed!");
        }
    }
}
