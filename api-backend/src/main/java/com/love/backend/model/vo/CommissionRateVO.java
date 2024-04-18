package com.love.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommissionRateVO implements Serializable {
    private Long id;
    private Long pendingId;
    private String bizName;
    private Long merchantId;
    private BigDecimal currRate;
    private LocalDateTime currEffectiveTime;
    private BigDecimal pendingRate;
    private LocalDateTime pendingEffectiveTime;
    private List<Brand> brands;

    @Data
    public static class Brand implements Serializable {
        private Long id;
        private String name;
        private String logo;
    }
}
