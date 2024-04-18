package com.love.marketplace.model.vo;

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
public class AfterSalesItemVO implements Serializable {
    private String skuImg;
    private String goodsTitle;
    private Long skuId;
    private String skuInfo;
    private BigDecimal price;
    private Integer qty;
    private Long goodsId;
    private String slug;
}
