package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CommissionRateSaveBO implements Serializable {
    private Long id;
    private BigDecimal rate;
    private Long adminId;
    private String bizName;
    private LocalDateTime effectiveTime;
}
