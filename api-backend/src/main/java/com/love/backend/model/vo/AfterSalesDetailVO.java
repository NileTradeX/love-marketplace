package com.love.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AfterSalesDetailVO implements Serializable {
    private String afterSaleRemark;
    private String afterSaleReason;
    private String afterSaleNo;
    private LocalDateTime createTime;
    private LocalDateTime merchantDealTime;
    private String orderNo;
    private String merOrderNo;
    private Long merOrderId;
    private Integer afterSaleStatus;
    private List<AfterSalesSkuVO> items;
    private Buyer buyer;
    private Receiver receiver;
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "Buyer")
    public static class Buyer implements Serializable {
        private String name;
        private String email;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "Receiver")
    public static class Receiver implements Serializable {
        private String name;
        private String phone;
        private String email;
        private String address;
    }
}
