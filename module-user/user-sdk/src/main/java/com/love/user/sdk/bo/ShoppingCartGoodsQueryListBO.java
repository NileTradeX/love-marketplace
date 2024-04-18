package com.love.user.sdk.bo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ShoppingCartGoodsQueryListBO implements Serializable {
    private Long userId;
    private Long goodsId;
    private Long skuId;
}
