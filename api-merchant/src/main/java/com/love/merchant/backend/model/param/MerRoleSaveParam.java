package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Schema(title = "MerRoleSaveParam")
public class MerRoleSaveParam implements Serializable {

    @Schema(description = "name", requiredMode = Schema.RequiredMode.REQUIRED, example = "admin")
    @NotEmpty(message = "role name can not be null")
    private String name;
}
