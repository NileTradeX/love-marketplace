package com.love.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "OrderVO")
public class OrderVO implements Serializable {
    private Long id;
    private Long merchantId;
    private String orderNo;
    private String closeReason;
    private Integer status;
    private Buyer buyer;
    private Goods goods;
    private BigDecimal taxes;
    private BigDecimal total;
    private Consignee receiver;
    private Shipment shipment;
    private Refund refund;
    private Payment payment;
    private Brand brand;
    private LocalDateTime createTime;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "Goods")
    public static class Goods implements Serializable {
        private Long id;
        private String title;
        private Long skuId;
        private String skuImg;
        private String skuInfo;
        private BigDecimal price;
        private Integer qty;
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
    @Schema(title = "Shipment")
    public static class Shipment implements Serializable {
        private BigDecimal fee;
        private String carriers;
        private String trackingNo;
        private String shippoTransId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "Refund")
    public static class Refund implements Serializable {
        private Long id;
        private Integer type;
        private BigDecimal amount;
        private String reason;
        private String comment;
        @Schema(description = "status > 1:pending 5:abnormal 10:closed")
        private Integer status;
        private LocalDateTime requestTime;
        private Integer orderStatus;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "Payment")
    public static class Payment implements Serializable {
        private String type;
        private Long amount;
        @Schema(description = "status > 0:Processing 1:Succeeded 2:Failed 3:Canceled")
        private Integer status;
        private LocalDateTime time;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "Brand")
    public static class Brand implements Serializable {
        private String name;
        private String logo;
    }
}
