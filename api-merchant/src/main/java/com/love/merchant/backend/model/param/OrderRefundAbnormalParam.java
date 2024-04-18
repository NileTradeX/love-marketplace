package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OrderRefundAbnormalParam implements Serializable {

    @NotNull(message = "refundId cannot be null")
    @Schema(description = "refundId", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long refundId;

    @Schema(description = "abnormal comment", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "comment cannot be null")
    private String comment;

    @Schema(hidden = true)
    private Long userId;
}
