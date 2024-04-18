package com.love.marketplace.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "MerchantOrderVO")
public class MerchantOrderVO implements Serializable {
    private Long id;
    private Long brandId;
    private Long merchantId;
    private String orderNo;
    private String merOrderNo;
    private BigDecimal totalAmount;
    private Integer status;
    private String reason;
    private LocalDateTime createTime;
    private Brand brand;
    private List<OrderItem> items;
    private Consignee receiver;
    private Shipment shipment;
    private Payment payment;
    private Buyer buyer;
    private boolean canRefund;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "Shipment")
    public static class Shipment implements Serializable {
        private String carriers;
        private String trackingNo;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "Brand")
    public static class Brand implements Serializable {
        private String name;
        private String logo;
        private String slug;
        private Long id;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "OrderItem")
    public static class OrderItem implements Serializable {
        private Long id;
        private Long goodsId;
        private String goodsTitle;
        private Long skuId;
        private BigDecimal price;
        private Integer qty;
        private Integer status;
        private Integer afterSalesStatus;
        private String carriers;
        private String trackingNo;
        private LocalDateTime deliveryTime;
        private LocalDateTime createTime;
        private String slug;
        private String skuImg;
        private String skuInfo;
        private BigDecimal total;
        private String reason;
        private ReviewVO review;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "Buyer")
    public static class Buyer implements Serializable {
        private Long id;
        private String name;
        private String avatar;
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "Consignee")
    public static class Consignee implements Serializable {
        private String name;
        private String phone;
        private String email;
        private String address;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "Payment")
    public static class Payment implements Serializable {
        private String type;
        private Long amount;
        private Integer status;
        private LocalDateTime time;
    }
}
