package com.love.common.config;

import lombok.Data;

import java.io.Serializable;

@Data
public class TemplateAlias implements Serializable {

    private String welcome;

    private String passwordReset;

    private String merchantInvitation;

    private String merchantReviewReject;

    private String merchantReviewApprove;

    private String orderSummaryMerchant;

    private String orderSummaryShopper;

    private String refundMerchant;

    private String orderShippedShopper;

    private String customerFreeGift;

}

