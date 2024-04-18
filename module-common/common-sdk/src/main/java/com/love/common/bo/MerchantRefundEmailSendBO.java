package com.love.common.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantRefundEmailSendBO implements Serializable {
    private String toEmail;
    private String refundRequestNumber;
    private String refundTime;
    private String merchantOrderNumber;
    private String refundTotalAmount;
    private List<EmailOrderItemBO> items;
    private String viewUrl;
}
