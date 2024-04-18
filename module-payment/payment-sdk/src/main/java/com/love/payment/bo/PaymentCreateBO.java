package com.love.payment.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreateBO implements Serializable {
    private Long amount;
    private Long appFee;
    private Long merchantId;
    private String orderNo;
    private Long userId;
    private String paymentId;
    private Integer channel;
}
