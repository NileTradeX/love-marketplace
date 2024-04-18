package com.love.marketplace.model.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdyenSplitFundsParam implements Serializable {
    private Long amount;
    private String currency;
    private String pspReference;
    private String merchantReference;
    private String orderNo;
}
