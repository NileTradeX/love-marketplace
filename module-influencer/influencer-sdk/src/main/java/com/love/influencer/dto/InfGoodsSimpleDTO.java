package com.love.influencer.dto;

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
public class InfGoodsSimpleDTO implements Serializable {
    private Long id;
    private Long goodsId;
    private Long influencerId;
    private BigDecimal commissionRate;
}
