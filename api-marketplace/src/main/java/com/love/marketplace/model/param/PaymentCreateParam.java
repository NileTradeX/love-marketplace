package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PaymentCreateParam implements Serializable {

    @Schema(description = "ext data", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ext info can not be null")
    private String ext;
}
