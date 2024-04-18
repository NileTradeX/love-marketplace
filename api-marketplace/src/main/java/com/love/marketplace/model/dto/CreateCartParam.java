package com.love.marketplace.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class CreateCartParam implements Serializable {
    private Long goodsId;
    private Long skuId;
    private Integer qty;
}
