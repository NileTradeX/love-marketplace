package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;

@Data
@Schema(title = "IngredientParam")
public class IngredientParam implements Serializable {

    @Schema(description = "Ingredient name", requiredMode = AUTO, example = "name")
    private String name;

    @Schema(description = "Ingredient amount", requiredMode = AUTO, example = "amount")
    private String amount;
}
