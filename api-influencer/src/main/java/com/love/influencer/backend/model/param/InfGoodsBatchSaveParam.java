package com.love.influencer.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class InfGoodsBatchSaveParam implements Serializable {
    @NotEmpty(message = "the selectedGoods list can't be null")
    @Schema(description = "selectedGoods list", requiredMode = REQUIRED)
    private List<InfGoodsSaveParam> selectedGoods;
    @NotNull(message = "status can not be null")
    private Integer status;
    @NotNull(message = "id can not be null")
    @Schema(description = "id", example = "1", hidden = true)
    private Long userId;
}
