package com.love.marketplace.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GoodsSkuVO implements Serializable {
    private Long id;
    private String cover;
    private String imgList;
    private String attrValues;
    private String attrValueJson;
    private Long goodsId;
    private BigDecimal price;
    private Integer onHandStock;
    private Integer availableStock;
    private Integer committedStock;
    private BigDecimal shippingWeight;
    private String shippingWeightUnit;
    private String gtin;
    private String mpn;
    private Integer defaultSku;
    private Integer status;
}
