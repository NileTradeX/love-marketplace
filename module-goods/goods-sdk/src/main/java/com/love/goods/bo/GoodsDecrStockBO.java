package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsDecrStockBO implements Serializable {
    private Long goodsId;
    private Integer decrNum;
}
