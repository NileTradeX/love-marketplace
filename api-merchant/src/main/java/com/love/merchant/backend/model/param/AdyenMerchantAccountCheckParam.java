package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdyenMerchantAccountCheckParam implements Serializable {

    @Schema(hidden = true)
    private Long userId;

}
