package com.love.user.sdk.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ShoppingCartGoodsSaveBO implements Serializable {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long skuId;
    private BigDecimal price;
    private Integer qty;
    private Long shippingTemplateId;
    private String influencerCode;
}
