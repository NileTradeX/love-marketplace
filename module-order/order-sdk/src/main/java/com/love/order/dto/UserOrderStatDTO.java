package com.love.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserOrderStatDTO implements Serializable {
    private int orders;
    private BigDecimal amountSpent = BigDecimal.ZERO;
}
