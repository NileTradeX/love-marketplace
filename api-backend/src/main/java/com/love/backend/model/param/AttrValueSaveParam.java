package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "AttrValueSaveParam")
public class AttrValueSaveParam implements Serializable {

    @Schema(description = "attribute value id", requiredMode = AUTO, example = "0")
    private Long id;

    @Schema(description = "attribute name id", requiredMode = AUTO, example = "1")
    private Long attrNameId;

    @NotBlank(message = "attribute value can't be blank")
    @Schema(description = "attribute value", requiredMode = REQUIRED, example = "red")
    private String value;

    @Schema(description = "sort num", requiredMode = AUTO, example = "1")
    private Integer sortNum;
}
