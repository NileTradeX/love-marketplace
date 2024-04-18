package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "OrderDetailParam")
public class OrderDetailParam implements Serializable {

    @Schema(description = "merchantOrderId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "merchantOrderId cannot be null")
    private Long merchantOrderId;

    @Schema(hidden = true)
    private Long userId;
}
