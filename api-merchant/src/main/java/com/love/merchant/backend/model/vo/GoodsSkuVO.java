package com.love.merchant.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GoodsSkuVO implements Serializable {
    private Long id;
    private String cover;
    private String imgList;
    @Schema(description = "Attribute values(format is Large/black)")
    private String attrValues;
    @Schema(description = "Attribute value json(format is [{attrId:, name:, valueId, value:,},{}])")
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
