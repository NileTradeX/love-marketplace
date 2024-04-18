package com.love.payment.bo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PaymentUpdateByOrderNoBO implements Serializable {
    private String orderNo;
    private String type;
    private Integer status;
    private LocalDateTime payTime;
}
