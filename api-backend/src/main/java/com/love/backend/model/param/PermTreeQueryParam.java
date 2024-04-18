package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "PermTreeQueryParam")
public class PermTreeQueryParam implements Serializable {

    @Schema(description = "top perm id", requiredMode = Schema.RequiredMode.AUTO)
    private Long id;

    @NotNull(message = "flag cannot be null")
    @Schema(description = "menu flag > 1:admin platform 2:merchant platform", requiredMode = Schema.RequiredMode.REQUIRED)
    private int flag;
}
