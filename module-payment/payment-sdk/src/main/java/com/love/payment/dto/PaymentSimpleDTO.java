package com.love.payment.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PaymentSimpleDTO implements Serializable {
    private String orderNo;
    private LocalDateTime payTime;
    private Integer status;
    private String type;
    private Long amount;
    private Integer channel;
}
