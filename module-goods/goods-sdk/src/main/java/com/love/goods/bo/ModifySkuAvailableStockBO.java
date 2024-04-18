package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifySkuAvailableStockBO implements Serializable {
    private Long skuId;
    private Integer availableStock;
}
