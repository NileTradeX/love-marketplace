package com.love.influencer.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfGoodsUpdateByIdBO implements Serializable {
    private Long goodsId;
    private Integer status;
    private Integer goodsStatus;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer communityScore;
}

