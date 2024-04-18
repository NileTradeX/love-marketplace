package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "MerUserAdminInvitationEditParam")
public class MerUserAdminInvitationEditParam implements Serializable {

    @NotNull(message = "id cannot be null")
    @Schema(description = "id", requiredMode = REQUIRED)
    private Long id;

    @NotBlank(message = "email cannot be null")
    @Schema(description = "email", requiredMode = REQUIRED)
    @Email(message = "email format error")
    private String email;
}
