package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(title = "MerChangeDefaultCarrierParam")
public class MerChangeDefaultCarrierParam {
    @NotNull(message = "User ID can not be null")
    @Schema(description = "User id", example = "1", hidden = true)
    private Long userId;

    @Schema(description = "Default carrier", example = "ups")
    private String defaultCarrier;
}
