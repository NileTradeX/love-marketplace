package com.love.payment.controller;

import com.love.common.result.Result;
import com.love.payment.bo.AdyenMerchantAccountCreateBO;
import com.love.payment.bo.AdyenMerchantAccountQueryBO;
import com.love.payment.bo.AdyenMerchantOnboardingLinkBO;
import com.love.payment.dto.AdyenMerchantAccountDTO;
import com.love.payment.dto.AdyenMerchantOnboardingLinkDTO;
import com.love.payment.service.AdyenMerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("merchantAccount/adyen")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdyenMerchantAccountController {

    private final AdyenMerchantService adyenMerchantService;

    @GetMapping("detail")
    public Result<AdyenMerchantAccountDTO> detail(AdyenMerchantAccountQueryBO merchantAccountQueryBO) {
        return Result.success(adyenMerchantService.queryAccount(merchantAccountQueryBO.getMerchantId()));
    }

    @PostMapping("createAccount")
    public Result<AdyenMerchantAccountDTO> createAccount(@RequestBody AdyenMerchantAccountCreateBO merchantAccountCreateBO) {
        return Result.success(adyenMerchantService.createAccount(merchantAccountCreateBO));
    }

    @PostMapping("createOnboardingLink")
    public Result<AdyenMerchantOnboardingLinkDTO> createOnboardingLink(@RequestBody AdyenMerchantOnboardingLinkBO merchantOnboardingLinkBO) {
        return Result.success(adyenMerchantService.createOnboardingLink(merchantOnboardingLinkBO));
    }

}
