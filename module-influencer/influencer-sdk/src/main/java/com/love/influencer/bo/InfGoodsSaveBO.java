package com.love.influencer.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InfGoodsSaveBO implements Serializable {
    private Long goodsId;
    private Long influencerId;
    private Integer salesVolume;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer communityScore;
    private Integer goodsStatus;
    private Integer availableStock;
    private BigDecimal commissionRate;
}

