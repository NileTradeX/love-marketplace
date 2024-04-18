package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(title = "SysRoleSaveParam")
public class SysRoleSaveParam implements Serializable {

    @Schema(description = "name", requiredMode = Schema.RequiredMode.REQUIRED, example = "admin")
    @NotBlank(message = "role name can not be null")
    private String name;
}
