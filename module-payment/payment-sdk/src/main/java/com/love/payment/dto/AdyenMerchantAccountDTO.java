package com.love.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdyenMerchantAccountDTO implements Serializable {
    private Long merchantId;
    private String balanceAccountId;
    private Integer status;
}
