package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "UserResetPasswordParam")
public class UserResetPasswordParam implements Serializable {

    @NotNull(message = "Id can not be null")
    @Schema(description = "User Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "123")
    private Long id;

    @NotBlank(message = "Password can not be null")
    @Length(max = 64, min = 8, message = "Password length must between 8 and 64")
    @Schema(description = "Password", requiredMode = Schema.RequiredMode.REQUIRED, example = "P@ssw0rd123")
    private String password;

}
