package com.love.goods.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GoodsSkuDTO implements Serializable {
    private Long id;
    private Long merchantId;
    private String cover;
    private String imgList;
    /**
     * format is Large/black
     */
    private String attrValues;
    /**
     * format is [{attrId:, name:, valueId, value:,},{}]
     */
    private String attrValueJson;
    private Long goodsId;
    private BigDecimal price;
    private Integer onHandStock;
    private Integer availableStock;
    private Integer committedStock;
    private Integer status;
    private BigDecimal shippingWeight;
    private String shippingWeightUnit;
    private String code;
    private String gtin;
    private String mpn;
    private Integer defaultSku;
}
