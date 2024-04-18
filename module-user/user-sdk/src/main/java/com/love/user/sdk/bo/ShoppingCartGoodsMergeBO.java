package com.love.user.sdk.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ShoppingCartGoodsMergeBO implements Serializable {
    private Long userId;
    private List<Item> itemList;

    @Data
    public static class Item{
        private Long goodsId;
        private Long skuId;
        private BigDecimal price;
        private Integer qty;
        private String influencerCode;
    }
}
