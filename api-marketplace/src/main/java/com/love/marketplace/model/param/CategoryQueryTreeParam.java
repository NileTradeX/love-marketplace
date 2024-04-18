package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;

@Data
@Schema(title = "CategoryQueryTreeParam")
public class CategoryQueryTreeParam implements Serializable {

    @Schema(description = "level", requiredMode = AUTO, example = "1")
    private Integer level;

    @Schema(description = "attachGoodsType", requiredMode = AUTO, example = "true", defaultValue = "false")
    private boolean attachGoodsType = false;

    @Schema(description = "type", requiredMode = AUTO, defaultValue = "1")
    @NotNull(message = "category type can not be null")
    private Integer type = 1;
}
