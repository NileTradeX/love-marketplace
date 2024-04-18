package com.love.influencer.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "UpdateInfluencerGoodsStatusParam")
public class InfGoodsUpdateStatusParam implements Serializable {

    @NotNull(message = "id can not be null")
    @Schema(description = "id", example = "1", hidden = true)
    private Long userId;
    @NotEmpty(message = "the influencer goods id list can't be null")
    @Schema(description = "influencer goods id list", requiredMode = REQUIRED)
    private List<Long> ids;
    @NotNull(message = "the influencer goods status can't be null")
    @Schema(description = "influencer goods status", requiredMode = REQUIRED)
    private Integer status;
}

