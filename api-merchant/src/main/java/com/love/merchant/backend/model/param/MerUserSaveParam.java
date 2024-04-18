package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "MerUserSaveParam")
public class MerUserSaveParam implements Serializable {

    @Schema(description = "account", requiredMode = REQUIRED, example = "evan", defaultValue = "evan")
    @Length(max = 32, message = "Account length must less than 32")
    private String account;

    @Schema(description = "username", requiredMode = REQUIRED, example = "evan chen", defaultValue = "evan chen")
    @Length(max = 32, message = "Username length must less than 32")
    private String username;

    @Schema(description = "password", requiredMode = REQUIRED, example = "66668888", defaultValue = "66668888")
    @Length(min = 8, max = 32, message = "Password length must between 8 and 32")
    private String password;

    @Schema(description = "roleId", requiredMode = REQUIRED, example = "1", defaultValue = "1")
    @NotNull(message = "Role id can not be null")
    private Long roleId;
}
