package com.love.influencer.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfGoodsStockUpdateBO implements Serializable {
    private Long goodsId;
    private Integer availableStock;
}

