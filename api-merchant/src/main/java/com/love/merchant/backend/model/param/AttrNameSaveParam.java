package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "AttrNameSaveParam")
public class AttrNameSaveParam implements Serializable {

    @Schema(description = "Attribute id", requiredMode = AUTO, example = "0")
    private Long id;

    @NotBlank(message = "Attribute name can't be null")
    @Schema(description = "Attribute name", requiredMode = REQUIRED, example = "name")
    private String name;

    @NotEmpty(message = "Attribute value can't be empty")
    @Schema(description = "Attribute values", requiredMode = REQUIRED)
    private List<@Valid AttrValueSaveParam> values;
}
