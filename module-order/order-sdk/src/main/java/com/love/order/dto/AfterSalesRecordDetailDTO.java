package com.love.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AfterSalesRecordDetailDTO implements Serializable {
    private String afterSaleNo;
    private LocalDateTime createTime;
    private LocalDateTime merchantDealTime;
    private String merOrderNo;
    private Integer afterSaleStatus;
    private Long buyerId;
    private Long brandId;
    private String orderNo;
    private BigDecimal refundAmount;
    private String afterSaleReason;
    private String merchantDealDesc;
    private List<AfterSalesSkuDTO> items;
}
