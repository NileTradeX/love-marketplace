package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "UserChangePasswordParam")
public class UserChangePasswordParam implements Serializable {

    @NotNull(message = "Id can not be null")
    @Schema(description = "user id", example = "1", hidden = true)
    private Long userId;

    @NotBlank(message = "New password can not be null")
    @Length(max = 64, message = "New password length must less than 64")
    @Schema(description = "New Password", example = "P@assw0rd123")
    private String newPassword;

    @NotBlank(message = "Old password can not be null")
    @Schema(description = "Old Password", example = "P@assw0rd234")
    @Length(max = 64, message = "Old password length must less than 64")
    private String oldPassword;
}
