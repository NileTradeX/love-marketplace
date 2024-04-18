package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdyenMerchantOnboardingLinkParam implements Serializable {

    @Schema(hidden = true)
    private Long userId;

    @Schema(description = "returnUrl", requiredMode = Schema.RequiredMode.AUTO)
    private String returnUrl;

    @Schema(description = "county", requiredMode = Schema.RequiredMode.AUTO)
    private String county;
}
