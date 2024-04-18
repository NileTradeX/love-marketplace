package com.love.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AfterSalesSkuDTO implements Serializable {
    private String skuImg;
    private String goodsTitle;
    private Long goodsId;
    private String slug;
    private Long skuId;
    private String skuInfo;
    private BigDecimal price;
    private Integer qty;
    private BigDecimal refundAmount;
}
