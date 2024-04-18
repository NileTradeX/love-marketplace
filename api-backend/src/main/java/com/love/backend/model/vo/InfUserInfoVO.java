package com.love.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InfUserInfoVO implements Serializable {
    private Long id;
    private String account;
    private String username;
    private String avatar;
    private String firstName;
    private String lastName;
    private String socialLinks;
    private String paypalAccount;
    private BigDecimal commissionRate;
    private String code;
}
