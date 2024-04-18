package com.love.payment.service;

import com.love.payment.bo.AdyenMerchantAccountCreateBO;
import com.love.payment.bo.AdyenMerchantOnboardingLinkBO;
import com.love.payment.dto.AdyenMerchantAccountDTO;
import com.love.payment.dto.AdyenMerchantOnboardingLinkDTO;
import com.love.payment.entity.AdyenMerchant;

import java.util.Optional;

public interface AdyenMerchantService {

    Optional<AdyenMerchant> queryByMerchantId(Long merchantId);

    AdyenMerchantAccountDTO createAccount(AdyenMerchantAccountCreateBO merchantAccountBO);

    AdyenMerchantAccountDTO queryAccount(Long merchantId);

    AdyenMerchantOnboardingLinkDTO createOnboardingLink(AdyenMerchantOnboardingLinkBO merchantOnboardingLinkBO);
}
