package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultiItemOrderSaveBO implements Serializable {
    private Long buyerId;
    private String buyerName;
    private Integer buyerType;
    private String orderNo;
    private BigDecimal shippingFee;
    private BigDecimal taxes;
    private BigDecimal appFee;
    private BigDecimal totalAmount;
    private String consignee;
    private String consigneePhone;
    private String consigneeEmail;
    private String consigneeAddress;
    private List<Brand> brands;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Brand implements Serializable {
        private Long merchantId;
        private Long brandId;
        private String brandName;
        private List<OrderItemSaveBO> items;
    }
}
