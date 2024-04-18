package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(title = "InfluencerUserHitsParam")
public class InfluencerUserHitsParam {

    @Schema(description = "displayName", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "displayName cannot be null")
    private String displayName;
}
