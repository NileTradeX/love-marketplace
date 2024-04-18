package com.love.merchant.backend.controller;

import com.love.common.result.Result;
import com.love.merchant.backend.manager.AdyenMerchantManager;
import com.love.merchant.backend.model.param.AdyenMerchantAccountCheckParam;
import com.love.merchant.backend.model.param.AdyenMerchantAccountCreateParam;
import com.love.merchant.backend.model.param.AdyenMerchantOnboardingLinkParam;
import com.love.merchant.backend.model.vo.AdyenMerchantAccountVO;
import com.love.merchant.backend.model.vo.AdyenMerchantOnboardingLinkVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("merchant/adyen")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "MerchantAdyenApi", description = "all Adyen seller manage operation")
public class AdyenMerchantController {

    private final AdyenMerchantManager adyenMerchantManager;


    @GetMapping("checkAccount")
    @Operation(operationId = "checkAccount")
    public Result<AdyenMerchantAccountVO> checkAccount(AdyenMerchantAccountCheckParam merchantAccountCheckParam) {
        return Result.success(adyenMerchantManager.checkAccount(merchantAccountCheckParam));
    }

    @PostMapping("createAccount")
    @Operation(operationId = "createAccount")
    public Result<AdyenMerchantAccountVO> createAccount(@RequestBody @Validated AdyenMerchantAccountCreateParam merchantAccountCreateParam) {
        return Result.success(adyenMerchantManager.createAccount(merchantAccountCreateParam));
    }

    @PostMapping("createOnboardingLink")
    @Operation(operationId = "createOnboardingLink")
    public Result<AdyenMerchantOnboardingLinkVO> createOnboardingLink(@RequestBody @Validated AdyenMerchantOnboardingLinkParam merchantOnboardingLinkParam) {
        return Result.success(adyenMerchantManager.createOnboardingLink(merchantOnboardingLinkParam));
    }

}
