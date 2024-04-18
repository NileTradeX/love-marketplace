package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "LabelSaveParam")
public class LabelSaveParam implements Serializable {

    @Schema(description = "Label id", requiredMode = AUTO, example = "0")
    private Long id;

    @NotBlank(message = "Label name can't be empty")
    @Schema(description = "Label name", requiredMode = REQUIRED, example = "name")
    private String name;

    @Schema(description = "Label type, 0: ingredient, 1: benefit ", requiredMode = AUTO, example = "0")
    private Integer type;
}
