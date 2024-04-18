package com.love.influencer.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InfUserOrderVO implements Serializable {
    private Long id;
    private Long influencerId;
    private Long orderId;
    private String orderItemNo;
    private Long buyerId;
    private BigDecimal totalAmount;
    private BigDecimal commission;
    private BigDecimal refundAmount;
    private LocalDateTime createTime;
}
