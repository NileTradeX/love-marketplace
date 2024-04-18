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
public class MerchantOrderSummaryEmailSendBO implements Serializable {
    private String toEmail;
    private String merchantName;
    private String merchantOrderNumber;
    private String orderDate;
    private List<EmailOrderItemBO> items;
    private String totalAmount;
    private String viewUrl;
}
