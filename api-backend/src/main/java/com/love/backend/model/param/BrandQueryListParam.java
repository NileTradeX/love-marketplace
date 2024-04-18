package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(type = "BrandQueryListParam")
public class BrandQueryListParam implements Serializable {

    @NotNull(message = "Merchant id can't be null")
    @Schema(description = "merchantId", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long merchantId;
}
