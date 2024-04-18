package com.love.user.sdk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartGoodsDTO implements Serializable {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long skuId;
    private BigDecimal price;
    private Integer qty;
    private Long shippingTemplateId;
    private String influencerCode;
}
