package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(type = "OrderRefundCreateParam")
public class OrderRefundCreateParam implements Serializable {
    @Schema(description = "orderId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "orderId cannot be null")
    private Long orderId;

    @Schema(description = "type > 1:Refund Only 2:Return and Refund", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "type cannot be null")
    private Integer type;

    @Schema(description = "reason", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "reason cannot be null")
    private String reason;

    @Schema(hidden = true)
    private Long userId;
}
