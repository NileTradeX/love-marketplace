package com.love.merchant.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "AdyenMerchantAccountVO")
public class AdyenMerchantAccountVO implements Serializable {
    private Long merchantId;
    private String balanceAccountId;
    @Schema(description = "0(NEED_ONBOARDING) 1(ONBOARDING_IN_PROCESS) 2(COMPLETED)")
    private Integer status;
}
