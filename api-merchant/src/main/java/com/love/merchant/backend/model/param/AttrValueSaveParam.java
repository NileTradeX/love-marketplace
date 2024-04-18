package com.love.merchant.backend.model.param;

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

    @Schema(description = "attribute name id", requiredMode = AUTO, example = "attrNameId")
    private Long attrNameId;

    @NotBlank(message = "Attribute value can't be blank")
    @Schema(description = "Attribute value", requiredMode = REQUIRED, example = "value")
    private String value;

    @Schema(description = "sort num value", example = "1")
    private Integer sortNum;
}
