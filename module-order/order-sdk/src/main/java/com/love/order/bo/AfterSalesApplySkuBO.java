package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (AfterSalesSku)表实体类
 *
 * @author eric
 * @since 2023-07-11 16:58:28
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AfterSalesApplySkuBO implements Serializable {
    private Long goodsId;

    private Long skuId;

    private Integer qty;

    private BigDecimal price;

    private String skuInfo;

    private String skuImg;
}

