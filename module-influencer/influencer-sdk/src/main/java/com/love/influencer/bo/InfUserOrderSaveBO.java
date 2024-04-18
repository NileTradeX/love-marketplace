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
public class InfUserOrderSaveBO implements Serializable {
    private Long influencerId;
    private String influencerCode;
    private Long orderId;
    private String orderItemNo;
    private Long buyerId;
    private BigDecimal totalAmount;
    private BigDecimal commission;
    private BigDecimal commissionRate;
    private BigDecimal merCommissionRate;
    private Long goodsId;
    private Long skuId;
}
