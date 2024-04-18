package com.love.common.client;

import com.love.common.bo.*;
import com.love.common.dto.EmailSendDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "common-service-api", contextId = "emailSendFeignClient")
public interface EmailSendFeignClient {
    @PostMapping("sendWelcomeEmail")
    EmailSendDTO sendWelcomeEmail(WelcomeEmailSendBO welcomeEmailSendBO);

    @PostMapping("sendPasswordResetEmail")
    EmailSendDTO sendPasswordResetEmail(PasswordResetEmailSendBO passwordResetEmailSendBO);

    @PostMapping("sendMerchantInvitationEmail")
    EmailSendDTO sendMerchantInvitationEmail(MerchantInvitationEmailSendBO merchantInvitationEmailSendBO);

    @PostMapping("sendMerchantReviewRejectEmail")
    EmailSendDTO sendMerchantReviewRejectEmail(MerchantReviewRejectEmailSendBO merchantReviewRejectEmailSendBO);

    @PostMapping("sendMerchantReviewApproveEmail")
    EmailSendDTO sendMerchantReviewApproveEmail(MerchantReviewApproveEmailSendBO merchantReviewApproveEmailSendBO);

    @PostMapping("sendMerchantOrderSummaryEmail")
    EmailSendDTO sendMerchantOrderSummaryEmail(MerchantOrderSummaryEmailSendBO merchantOrderSummaryEmailSendBO);

    @PostMapping("sendShopperOrderSummaryEmail")
    EmailSendDTO sendShopperOrderSummaryEmail(ShopperOrderSummaryEmailSendBO shopperOrderSummaryEmailSendBO);

    @PostMapping("sendMerchantRefundEmail")
    EmailSendDTO sendMerchantRefundEmail(MerchantRefundEmailSendBO merchantRefundEmailSendBO);

    @PostMapping("sendShopperOrderShippedEmail")
    EmailSendDTO sendShopperOrderShippedEmail(ShopperOrderShippedEmailSendBO shopperOrderShippedEmailSendBO);

    @PostMapping("sendFreeGiftEmail")
    EmailSendDTO sendFreeGiftEmail(CustomerFreeGiftSendBO customerFreeGiftSendBO);
}
