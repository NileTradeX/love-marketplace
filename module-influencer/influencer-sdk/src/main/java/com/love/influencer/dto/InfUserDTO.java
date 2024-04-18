package com.love.influencer.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class InfUserDTO implements Serializable {
    private Long id;
    private String account;
    private String username;
    private String firstName;
    private String lastName;
    private String code;
    private Integer status;
    private String paypalAccount;
    private BigDecimal commissionRate;
    private LocalDateTime lastLoginTime;
}
