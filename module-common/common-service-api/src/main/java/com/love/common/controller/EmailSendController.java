package com.love.common.controller;

import com.love.common.bo.*;
import com.love.common.dto.EmailSendDTO;
import com.love.common.result.Result;
import com.love.common.service.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailSendController {

    private final EmailSendService emailSendService;

    @PostMapping("sendWelcomeEmail")
    public Result<EmailSendDTO> sendWelcomeEmail(@RequestBody WelcomeEmailSendBO welcomeEmailSendBO) {
        return Result.success(emailSendService.sendWelcomeEmail(welcomeEmailSendBO));
    }

    @PostMapping("sendPasswordResetEmail")
    public Result<EmailSendDTO> sendPasswordResetEmail(@RequestBody PasswordResetEmailSendBO passwordResetEmailSendBO) {
        return Result.success(emailSendService.sendPasswordResetEmail(passwordResetEmailSendBO));
    }

    @PostMapping("sendMerchantInvitationEmail")
    public Result<EmailSendDTO> sendPasswordResetEmail(@RequestBody MerchantInvitationEmailSendBO merchantInvitationEmailSendBO) {
        return Result.success(emailSendService.sendMerchantInvitationEmail(merchantInvitationEmailSendBO));
    }

    @PostMapping("sendMerchantReviewRejectEmail")
    public Result<EmailSendDTO> sendMerchantReviewRejectEmail(@RequestBody MerchantReviewRejectEmailSendBO merchantReviewRejectEmailSendBO) {
        return Result.success(emailSendService.sendMerchantReviewRejectEmail(merchantReviewRejectEmailSendBO));
    }

    @PostMapping("sendMerchantReviewApproveEmail")
    public Result<EmailSendDTO> sendMerchantReviewApproveEmail(@RequestBody MerchantReviewApproveEmailSendBO merchantReviewApproveEmailSendBO) {
        return Result.success(emailSendService.sendMerchantReviewApproveEmail(merchantReviewApproveEmailSendBO));
    }

    @PostMapping("sendMerchantOrderSummaryEmail")
    public Result<EmailSendDTO> sendMerchantOrderSummaryEmail(@RequestBody MerchantOrderSummaryEmailSendBO merchantOrderSummaryEmailSendBO) {
        return Result.success(emailSendService.sendMerchantOrderSummaryEmail(merchantOrderSummaryEmailSendBO));
    }

    @PostMapping("sendShopperOrderSummaryEmail")
    public Result<EmailSendDTO> sendShopperOrderSummaryEmail(@RequestBody ShopperOrderSummaryEmailSendBO shopperOrderSummaryEmailSendBO) {
        return Result.success(emailSendService.sendShopperOrderSummaryEmail(shopperOrderSummaryEmailSendBO));
    }

    @PostMapping("sendMerchantRefundEmail")
    public Result<EmailSendDTO> sendMerchantRefundEmail(@RequestBody MerchantRefundEmailSendBO merchantRefundEmailSendBO) {
        return Result.success(emailSendService.sendMerchantRefundEmail(merchantRefundEmailSendBO));
    }

    @PostMapping("sendShopperOrderShippedEmail")
    public Result<EmailSendDTO> sendShopperOrderShippedEmail(@RequestBody ShopperOrderShippedEmailSendBO shopperOrderShippedEmailSendBO) {
        return Result.success(emailSendService.sendShopperOrderShippedEmail(shopperOrderShippedEmailSendBO));
    }

    @PostMapping("sendFreeGiftEmail")
    public Result<EmailSendDTO> sendFreeGiftEmail(@RequestBody CustomerFreeGiftSendBO customerFreeGiftSendBO) {
        return Result.success(emailSendService.sendFreeGiftEmail(customerFreeGiftSendBO));
    }
}
