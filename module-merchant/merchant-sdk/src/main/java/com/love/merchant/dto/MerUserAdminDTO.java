package com.love.merchant.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MerUserAdminDTO implements Serializable {
    private Long id;
    private String account;
    private String username;
    private Integer status;
    private String reason;
    private String mpa;
    private BigDecimal commissionFeeRate;
    private LocalDateTime createTime;
    private MerUserAdminPersonalInfoDTO personalInfo;
    private MerUserAdminBusinessInfoDTO businessInfo;
}
