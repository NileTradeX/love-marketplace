package com.love.merchant.backend.controller;

import com.love.common.result.Result;
import com.love.merchant.backend.manager.MerchantManager;
import com.love.merchant.backend.model.param.*;
import com.love.merchant.backend.model.vo.MerUserAdminFullVO;
import com.love.merchant.backend.model.vo.MerUserAdminInvitationVO;
import com.love.merchant.backend.model.vo.MerUserAdminVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("merchant")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "MerUserApi", description = "merchant manage operation")
public class MerchantController {

    private final MerchantManager merchantManager;

    @PostMapping("register")
    @Operation(operationId = "merchantRegister")
    public Result<MerUserAdminVO> register(@RequestBody @Validated MerUserAdminSaveParam merUserAdminSaveParam) {
        return Result.success(merchantManager.register(merUserAdminSaveParam));
    }

    @GetMapping("validateToken")
    @Operation(operationId = "validateToken")
    public Result<Boolean> validateToken(@Validated TokenParam tokenParam) {
        return Result.success(merchantManager.validateToken(tokenParam));
    }

    @GetMapping("detail")
    @Operation(operationId = "merchantDetail")
    public Result<MerUserAdminFullVO> detail(MerUserAdminIdParam merUserAdminIdParam) {
        return Result.success(merchantManager.detail(merUserAdminIdParam));
    }

    @GetMapping("businessInfo")
    @Operation(operationId = "merchantBusinessInfo")
    public Result<MerUserAdminFullVO.MerUserAdminBusinessInfoVO> businessInfo(MerUserAdminIdParam merUserAdminIdParam) {
        return Result.success(merchantManager.businessInfo(merUserAdminIdParam));
    }


    @GetMapping("invitation/code")
    @Operation(operationId = "queryInvitationByCode")
    public Result<MerUserAdminInvitationVO> queryByCode(MerUserAdminInvitationQueryByCodeParam merUserAdminInvitationQueryByCodeParam) {
        return Result.success(merchantManager.queryByCode(merUserAdminInvitationQueryByCodeParam));
    }

    @PostMapping("changeBizOrderMgmtEmail")
    @Operation(operationId = "changeBizOrderMgmtEmail")
    public Result<Boolean> changeBizOrderMgmtEmail(@RequestBody MerChangeBizOrderMgmtEmailParam merChangeBizOrderMgmtEmailParam) {
        return Result.success(merchantManager.changeBizOrderMgmtEmail(merChangeBizOrderMgmtEmailParam));
    }

    @PostMapping("changeDefaultCarrier")
    @Operation(operationId = "changeDefaultCarrier")
    public Result<Boolean> changeDefaultCarrier(@RequestBody MerChangeDefaultCarrierParam merChangeDefaultCarrierParam) {
        return Result.success(merchantManager.changeDefaultCarrier(merChangeDefaultCarrierParam));
    }
}
