package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;

@Data
@Schema(title = "CategoryQueryTreeParam")
public class CategoryQueryTreeParam implements Serializable {

    @Schema(description = "level", requiredMode = AUTO, example = "1")
    private Integer level;

    @Schema(description = "attachGoodsType", requiredMode = AUTO, example = "true", defaultValue = "true")
    private boolean attachGoodsType = true;

    @Schema(description = "type", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer type = 0;
}
