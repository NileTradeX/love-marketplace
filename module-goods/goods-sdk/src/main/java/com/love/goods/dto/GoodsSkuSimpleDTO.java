package com.love.goods.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GoodsSkuSimpleDTO implements Serializable {
    private Long id;
    private Long merchantId;
    private BigDecimal price;
    private Integer onHandStock;
    private Integer availableStock;
    private Integer committedStock;
}
