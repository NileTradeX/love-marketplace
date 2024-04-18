package com.love.payment.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdyenMerchantOnboardingLinkBO implements Serializable {
    private Long merchantId;
    private String county;
    private String redirectUrl;
}
