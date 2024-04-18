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
public class ShopperOrderSummaryEmailSendBO implements Serializable {
    private String toEmail;
    private String merchantOrderNumber;
    private String orderDate;
    private List<EmailOrderItemBO> items;
    private String shippingMethod;
    private String shippingFee;
    private String taxes;
    private String shippingAddress;
    private String totalAmount;
    private String viewUrl;
}
