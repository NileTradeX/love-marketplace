package com.love.influencer.backend.model.param;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InfUserOrderSaveParam implements Serializable {
    private Long influencerId;
    private Long orderId;
    private String orderNo;
    private Long buyerId;
    private BigDecimal totalAmount;
    private BigDecimal commission;
    private BigDecimal commissionRate;
    private BigDecimal merCommissionRate;
    private Long goodsId;
    private Long skuId;
}
