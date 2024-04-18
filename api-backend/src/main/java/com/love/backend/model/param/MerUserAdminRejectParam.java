package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "MerUserAdminRejectParam")
public class MerUserAdminRejectParam implements Serializable {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "user id cannot be null")
    private Long id;

    @Schema(description = "reason", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "reject reason cannot be null")
    private String reason;
}
