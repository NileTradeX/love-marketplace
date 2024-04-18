package com.love.influencer.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "InfluencerDashboardParam")
public class DashboardParam implements Serializable {

    @NotNull(message = "Id can not be null")
    @Schema(description = "user id", example = "1", hidden = true)
    private Long userId;
}
