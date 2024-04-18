package com.love.influencer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfUserOrderDTO implements Serializable {
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
