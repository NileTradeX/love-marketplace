package com.love.common.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopperOrderShippedEmailSendBO {
    private String toEmail;
    private String carrier;
    private String trackingNumber;
    private String shippingAddress;
    private String shippingMethod;
    private String deliveryMethod;
    private String orderDate;
    private List<EmailOrderItemBO> items;
    private String viewUrl;
}
