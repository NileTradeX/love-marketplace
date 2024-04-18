package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyOrderCreateBO implements Serializable {
    private Order order;
    private List<OrderItem> orderItems;
    private String shopifyMerchantName;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order implements Serializable {
        private Long id;
        private String orderNo;
        private Integer buyerType;
        private Long buyerId;
        private String buyerName;
        private BigDecimal taxes;
        private BigDecimal shippingFee;
        private BigDecimal appFee;
        private BigDecimal totalAmount;
        private Integer status;
        private String consignee;
        private String consigneePhone;
        private String consigneeEmail;
        private String consigneeAddress;
        private LocalDateTime createTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem implements Serializable {
        private String goodsTitle;
        private String goodsImage;
        private String skuId;
        private String skuSpec;
        private String unitPrice;
        private String quantity;
        private String itemTotalAmount;
    }
}
