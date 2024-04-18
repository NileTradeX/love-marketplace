package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BoltPaymentCreateParam implements Serializable {
    @Schema(description = "orderReference", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "orderReference cannot be blank")
    private String orderReference;
    @Schema(description = "transactionReference", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "transactionReference cannot be blank")
    private String transactionReference;
    @Schema(description = "total amount", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "total amount can not be null")
    private Long totalAmount;
}
