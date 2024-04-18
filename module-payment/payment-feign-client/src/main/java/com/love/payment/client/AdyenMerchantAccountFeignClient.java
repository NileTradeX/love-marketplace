package com.love.payment.client;

import com.love.payment.bo.AdyenMerchantAccountCreateBO;
import com.love.payment.bo.AdyenMerchantAccountQueryBO;
import com.love.payment.bo.AdyenMerchantOnboardingLinkBO;
import com.love.payment.dto.AdyenMerchantAccountDTO;
import com.love.payment.dto.AdyenMerchantOnboardingLinkDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-service-api", contextId = "adyenMerchantAccountFeignClient", path = "merchantAccount/adyen")
public interface AdyenMerchantAccountFeignClient {
    @GetMapping("detail")
    AdyenMerchantAccountDTO detail(@SpringQueryMap AdyenMerchantAccountQueryBO merchantAccountQueryBO);

    @PostMapping("createAccount")
    AdyenMerchantAccountDTO createAccount(AdyenMerchantAccountCreateBO merchantAccountCreateBO);

    @PostMapping("createOnboardingLink")
    AdyenMerchantOnboardingLinkDTO createOnboardingLink(AdyenMerchantOnboardingLinkBO merchantOnboardingLinkBO);

}
