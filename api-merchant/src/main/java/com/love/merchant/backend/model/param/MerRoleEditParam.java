package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "MerRoleEditParam")
public class MerRoleEditParam implements Serializable {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Role id can not be null")
    private Long id;

    @Schema(description = "name", requiredMode = Schema.RequiredMode.REQUIRED, example = "admin")
    @NotBlank(message = "Role name can not empty")
    private String name;
}
