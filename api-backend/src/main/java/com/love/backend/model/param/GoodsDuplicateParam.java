package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "GoodsDuplicateParam")
public class GoodsDuplicateParam implements Serializable {

    @NotNull(message = "the goods id can't be null")
    @Schema(description = "goods id", requiredMode = REQUIRED, example = "0")
    private Long id;

    @NotBlank(message = "the product name can't be empty")
    @Schema(description = "goods name", requiredMode = REQUIRED, example = "goods title")
    private String name;
}
