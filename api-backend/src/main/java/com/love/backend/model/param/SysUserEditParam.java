package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "SysUserEditParam")
public class SysUserEditParam implements Serializable {

    @Schema(description = "sysUser id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "User id can not be null")
    private Long id;

    @Schema(description = "username", requiredMode = Schema.RequiredMode.AUTO, example = "ruoran")
    @NotBlank(message = "User name can not be null")
    private String username;

    @Schema(description = "roleId", requiredMode = Schema.RequiredMode.AUTO, example = "1")
    private Long roleId;
}
