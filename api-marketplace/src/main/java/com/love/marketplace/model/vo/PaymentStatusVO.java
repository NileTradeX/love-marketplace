package com.love.marketplace.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PaymentStatusVO implements Serializable {
    private String paymentId;
    private Integer status;
    private Long orderId;
    private BigDecimal totalAmount;
    private String orderTotalCurrency;
}
