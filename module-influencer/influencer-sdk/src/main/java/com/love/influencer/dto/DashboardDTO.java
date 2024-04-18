package com.love.influencer.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class DashboardDTO implements Serializable {
    private Long totalStorefrontClick = 0L;
    private BigDecimal totalTransactionVolume = new BigDecimal(0);
    private BigDecimal totalCommissionEarnings = new BigDecimal(0);
    private Long weeklyStorefrontClick = 0L;
    private BigDecimal weeklyTransactionVolume = new BigDecimal(0);
    private BigDecimal weeklyCommissionEarnings = new BigDecimal(0);

    private Long monthlyStorefrontClick = 0L;
    private BigDecimal monthlyTransactionVolume = new BigDecimal(0);
    private BigDecimal monthlyCommissionEarnings = new BigDecimal(0);

    private BigDecimal balance = new BigDecimal(0);
}
