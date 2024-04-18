package com.love.merchant.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.common.exception.BizException;
import com.love.merchant.backend.model.param.AdyenMerchantAccountCheckParam;
import com.love.merchant.backend.model.param.AdyenMerchantAccountCreateParam;
import com.love.merchant.backend.model.param.AdyenMerchantOnboardingLinkParam;
import com.love.merchant.backend.model.vo.AdyenMerchantAccountVO;
import com.love.merchant.backend.model.vo.AdyenMerchantOnboardingLinkVO;
import com.love.merchant.bo.MerUserAdminQueryBO;
import com.love.merchant.client.MerchantUserFeignClient;
import com.love.merchant.dto.MerUserAdminBusinessInfoDTO;
import com.love.merchant.dto.MerUserAdminDTO;
import com.love.payment.bo.AdyenMerchantAccountCreateBO;
import com.love.payment.bo.AdyenMerchantAccountQueryBO;
import com.love.payment.bo.AdyenMerchantOnboardingLinkBO;
import com.love.payment.client.AdyenMerchantAccountFeignClient;
import com.love.payment.dto.AdyenMerchantAccountDTO;
import com.love.payment.dto.AdyenMerchantOnboardingLinkDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdyenMerchantManager {

    private static final Map<String, String> COUNTRY_ISO_3166_1 = new TreeMap<>();

    static {
        COUNTRY_ISO_3166_1.put("australia", "AU");
        COUNTRY_ISO_3166_1.put("singapore", "SG");
        COUNTRY_ISO_3166_1.put("united states", "US");
        COUNTRY_ISO_3166_1.put("canada", "CA");
        COUNTRY_ISO_3166_1.put("austria", "AT");
        COUNTRY_ISO_3166_1.put("belgium", "BE");
        COUNTRY_ISO_3166_1.put("bulgaria", "BG");
        COUNTRY_ISO_3166_1.put("croatia", "HR");
        COUNTRY_ISO_3166_1.put("cyprus", "CY");
        COUNTRY_ISO_3166_1.put("czech republic", "CZ");
        COUNTRY_ISO_3166_1.put("denmark", "DK");
        COUNTRY_ISO_3166_1.put("estonia", "EE");
        COUNTRY_ISO_3166_1.put("finland", "FI");
        COUNTRY_ISO_3166_1.put("france", "FR");
        COUNTRY_ISO_3166_1.put("germany", "DE");
        COUNTRY_ISO_3166_1.put("greece", "GR");
        COUNTRY_ISO_3166_1.put("hungary", "HU");
        COUNTRY_ISO_3166_1.put("ireland", "IE");
        COUNTRY_ISO_3166_1.put("italy", "IT");
        COUNTRY_ISO_3166_1.put("latvia", "LV");
        COUNTRY_ISO_3166_1.put("liechtenstein", "LI");
        COUNTRY_ISO_3166_1.put("lithuania", "LT");
        COUNTRY_ISO_3166_1.put("luxembourg", "LU");
        COUNTRY_ISO_3166_1.put("monaco", "MC");
        COUNTRY_ISO_3166_1.put("netherlands", "NL");
        COUNTRY_ISO_3166_1.put("norway", "NO");
        COUNTRY_ISO_3166_1.put("poland", "PL");
        COUNTRY_ISO_3166_1.put("portugal", "PT");
        COUNTRY_ISO_3166_1.put("romania", "RO");
        COUNTRY_ISO_3166_1.put("slovakia", "SK");
        COUNTRY_ISO_3166_1.put("slovenia", "SI");
        COUNTRY_ISO_3166_1.put("spain", "ES");
        COUNTRY_ISO_3166_1.put("sweden", "SE");
        COUNTRY_ISO_3166_1.put("switzerland", "CH");
        COUNTRY_ISO_3166_1.put("united kingdom", "GB");
    }

    private final AdyenMerchantAccountFeignClient adyenMerchantAccountFeignClient;
    private final MerchantUserFeignClient merchantUserFeignClient;

    public AdyenMerchantAccountVO checkAccount(AdyenMerchantAccountCheckParam merchantAccountCheckParam) {
        AdyenMerchantAccountDTO merchantAccountDTO = adyenMerchantAccountFeignClient.detail(AdyenMerchantAccountQueryBO.builder().merchantId(getAdminId(merchantAccountCheckParam.getUserId())).build());
        return BeanUtil.copyProperties(merchantAccountDTO, AdyenMerchantAccountVO.class);
    }

    public AdyenMerchantAccountVO createAccount(AdyenMerchantAccountCreateParam merchantAccountCreateParam) {
        Long adminId = getAdminId(merchantAccountCreateParam.getUserId());
        MerUserAdminDTO admin = merchantUserFeignClient.queryAdminById(MerUserAdminQueryBO.builder().userId(adminId).build());
        MerUserAdminBusinessInfoDTO businessInfo = admin.getBusinessInfo();
        String country = businessInfo.getCountry();
        if (Objects.isNull(country) || !COUNTRY_ISO_3166_1.containsKey(country.toLowerCase())) {
            throw BizException.build("country : {} ISO 3166-1 code error");
        }
        AdyenMerchantAccountDTO merchantAccountDTO = adyenMerchantAccountFeignClient.createAccount(AdyenMerchantAccountCreateBO.builder().country(COUNTRY_ISO_3166_1.get(country.toLowerCase())).legalName(businessInfo.getBizName()).merchantId(getAdminId(merchantAccountCreateParam.getUserId())).build());
        return BeanUtil.copyProperties(merchantAccountDTO, AdyenMerchantAccountVO.class);
    }

    public AdyenMerchantOnboardingLinkVO createOnboardingLink(AdyenMerchantOnboardingLinkParam merchantOnboardingLinkParam) {
        AdyenMerchantOnboardingLinkBO merchantOnboardingLinkBO = AdyenMerchantOnboardingLinkBO.builder().merchantId(getAdminId(merchantOnboardingLinkParam.getUserId())).county(merchantOnboardingLinkParam.getCounty()).redirectUrl(merchantOnboardingLinkParam.getReturnUrl()).build();
        AdyenMerchantOnboardingLinkDTO merchantOnboardingLinkDTO = adyenMerchantAccountFeignClient.createOnboardingLink(merchantOnboardingLinkBO);
        return BeanUtil.copyProperties(merchantOnboardingLinkDTO, AdyenMerchantOnboardingLinkVO.class);
    }

    private Long getAdminId(Long userId) {
        return merchantUserFeignClient.queryAdminById(MerUserAdminQueryBO.builder().userId(userId).simple(true).build()).getId();
    }
}
