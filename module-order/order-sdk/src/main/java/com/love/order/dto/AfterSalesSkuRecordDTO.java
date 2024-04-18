package com.love.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AfterSalesSkuRecordDTO implements Serializable {

    private Long merchantId;

    private String orderNo;

    private Long buyerId;

    private String afterSaleNo;

    private Long goodsId;

    private Long skuId;

    private BigDecimal price;
    private Integer qty;
    private BigDecimal refundAmount;

    private String merOrderNo;

    private String goodsTitle;

    private String skuImg;

    private String skuInfo;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    // apply info
    private AfterSalesRecordDTO afterSalesRecordDTO;
}
