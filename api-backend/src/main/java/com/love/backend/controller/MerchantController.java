package com.love.backend.controller;

import com.love.backend.manager.MerchantManager;
import com.love.backend.model.param.*;
import com.love.backend.model.vo.*;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.merchant.dto.MerUserAdminApproveDTO;
import com.love.merchant.dto.MerUserAdminRejectDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("merchant")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "MerchantApi")
public class MerchantController {

    private final MerchantManager merchantManager;

    @PostMapping("invitation/send")
    @Operation(operationId = "sendMerchantInvitation")
    public Result<MerUserAdminInvitationVO> send(@RequestBody @Validated MerUserAdminInvitationSaveParam merUserAdminInvitationSaveParam) {
        return Result.success(merchantManager.send(merUserAdminInvitationSaveParam));
    }

    @PostMapping("invitation/resend")
    @Operation(operationId = "resendMerchantInvitation")
    public Result<Boolean> resend(@RequestBody @Validated MerUserAdminInvitationEditParam merUserAdminInvitationEditParam) {
        return Result.success(merchantManager.resend(merUserAdminInvitationEditParam));
    }

    @GetMapping("invitation/page")
    @Operation(operationId = "queryMerchantInvitationPage", description = "invitation history")
    public Result<Pageable<MerUserAdminInvitationVO>> page(MerUserAdminInvitationQueryPageParam merUserAdminInvitationQueryPageParam) {
        return Result.success(merchantManager.page(merUserAdminInvitationQueryPageParam));
    }

    @GetMapping("simple/page")
    @Operation(operationId = "queryMerAdminSimplePage", description = "for goods list left menu")
    public Result<Pageable<MerUserAdminSimpleVO>> queryAdminSimplePage(MerUserAdminSimpleQueryPageParam merUserAdminSimpleQueryPageParam) {
        return Result.success(merchantManager.simpleAdminPage(merUserAdminSimpleQueryPageParam));
    }

    @GetMapping("full/page")
    @Operation(operationId = "queryMerAdminFullPage", description = "for merchant list")
    public Result<Pageable<MerUserAdminFullVO>> queryAdminFullPage(MerUserAdminFullQueryPageParam merUserAdminFullQueryPageParam) {
        return Result.success(merchantManager.fullAdminPage(merUserAdminFullQueryPageParam));
    }

    @GetMapping("detail")
    @Operation(operationId = "merchantDetail")
    public Result<MerUserAdminFullVO> detail(IdParam idParam) {
        return Result.success(merchantManager.detail(idParam));
    }

    @GetMapping("review/stat")
    @Operation(operationId = "merAdminReviewStat")
    public Result<MerUserAdminReviewStatVO> reviewStat(MerUserAdminReviewStatParam merUserAdminReviewStatParam) {
        return Result.success(merchantManager.reviewStat(merUserAdminReviewStatParam));
    }

    @PostMapping("approve")
    @Operation(operationId = "merchantApprove")
    public Result<MerUserAdminApproveDTO> merchantApprove(@RequestBody @Validated MerUserAdminApproveParam merUserAdminApproveParam) {
        return Result.success(merchantManager.approve(merUserAdminApproveParam));
    }

    @PostMapping("reject")
    @Operation(operationId = "merchantReject")
    public Result<MerUserAdminRejectDTO> merchantReject(@RequestBody @Validated MerUserAdminRejectParam merUserAdminRejectParam) {
        return Result.success(merchantManager.reject(merUserAdminRejectParam));
    }

    @GetMapping("fee/rate/page")
    @Operation(operationId = "feeRatePage")
    public Result<Pageable<CommissionRateVO>> feeRatePage(CommissionRateQueryPageParam commissionRateQueryPageParam) {
        return Result.success(merchantManager.feeRatePage(commissionRateQueryPageParam));
    }

    @GetMapping("fee/rate/edit")
    @Operation(operationId = "feeRateEdit")
    public Result<Boolean> feeRateEdit(CommissionRateSaveParam commissionRateSaveParam) {
        return Result.success(merchantManager.feeRateEdit(commissionRateSaveParam));
    }
}
