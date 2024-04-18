package com.love.merchant.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CommissionRateDTO implements Serializable {
    private Long id;
    private Long pendingId;
    private String bizName;
    private Long merchantId;
    private BigDecimal currRate;
    private LocalDateTime currEffectiveTime;
    private BigDecimal pendingRate;
    private LocalDateTime pendingEffectiveTime;
}
