package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OrderRefundCloseParam implements Serializable {

    @Schema(description = "refundId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "refundId cannot be null")
    private Long refundId;

    @Schema(hidden = true)
    private Long userId;
}
