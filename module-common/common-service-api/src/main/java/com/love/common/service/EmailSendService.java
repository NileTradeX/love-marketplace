package com.love.common.service;

import com.love.common.bo.*;
import com.love.common.dto.EmailSendDTO;

public interface EmailSendService {

    EmailSendDTO send(EmailSendBO emailSendBO) throws Exception;

    EmailSendDTO sendWelcomeEmail(WelcomeEmailSendBO welcomeEmailSendBO);

    EmailSendDTO sendPasswordResetEmail(PasswordResetEmailSendBO passwordResetEmailSendBO);

    EmailSendDTO sendMerchantInvitationEmail(MerchantInvitationEmailSendBO merchantInvitationEmailSendBO);

    EmailSendDTO sendMerchantReviewRejectEmail(MerchantReviewRejectEmailSendBO merchantReviewRejectEmailSendBO);

    EmailSendDTO sendMerchantReviewApproveEmail(MerchantReviewApproveEmailSendBO merchantReviewApproveEmailSendBO);

    EmailSendDTO sendMerchantOrderSummaryEmail(MerchantOrderSummaryEmailSendBO merchantOrderSummaryEmailSendBO);

    EmailSendDTO sendShopperOrderSummaryEmail(ShopperOrderSummaryEmailSendBO shopperOrderSummaryEmailSendBO);

    EmailSendDTO sendMerchantRefundEmail(MerchantRefundEmailSendBO merchantRefundEmailSendBO);

    EmailSendDTO sendShopperOrderShippedEmail(ShopperOrderShippedEmailSendBO shopperOrderShippedEmailSendBO);

    EmailSendDTO sendFreeGiftEmail(CustomerFreeGiftSendBO customerFreeGiftSendBO);
}
