package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "MerUserAdminInvitationSaveParam")
public class MerUserAdminInvitationSaveParam implements Serializable {

    @NotBlank(message = "business name cannot be null")
    @Schema(description = "bizName", requiredMode = REQUIRED)
    private String bizName;

    @NotBlank(message = "email cannot be null")
    @Email(message = "email format error")
    @Schema(description = "email", requiredMode = REQUIRED)
    private String email;

    @NotNull(message = "commission fee rate cannot be null")
    @Schema(description = "commissionFeeRate", requiredMode = REQUIRED)
    private BigDecimal commissionFeeRate;

    @Schema(description = "Master Purchase Agreement", requiredMode = NOT_REQUIRED)
    private String mpa;
}
