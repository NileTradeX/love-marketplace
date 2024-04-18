package com.love.backend.model.vo;

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
    private String merOrderNo;
    private BigDecimal totalAmount;
    private Integer status;
    private LocalDateTime createTime;
    private List<OrderItem> items;
    private Consignee receiver;
    private Shipment shipment;
    private Buyer buyer;

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
    @Schema(title = "OrderItem")
    public static class OrderItem implements Serializable {
        private Long id;
        private Long goodsId;
        private String goodsTitle;
        private Long skuId;
        private BigDecimal price;
        private Integer qty;
        private Integer status;
        private String carriers;
        private String trackingNo;
        private LocalDateTime deliveryTime;
        private LocalDateTime createTime;
        private String skuImg;
        private String skuInfo;
        private BigDecimal total;
        private String afterSalesNo;
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
    @Schema(title = "Buyer")
    public static class Buyer implements Serializable {
        private Long id;
        private String name;
        private String avatar;
        private String email;
    }
}
