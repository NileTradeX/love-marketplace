package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(type = "PaymentStatusParam")
public class PaymentStatusParam implements Serializable {

    @Schema(description = "paymentId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "paymentId can't be null")
    private String paymentId;
}
