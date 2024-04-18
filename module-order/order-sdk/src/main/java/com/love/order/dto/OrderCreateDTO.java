package com.love.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDTO implements Serializable {
    private Long id;
    private Long merchantId;
    private String orderNo;
    private BigDecimal amount;
    private BigDecimal appFee;
    private Long userId;
}
