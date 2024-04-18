package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
@Schema(title = "UserLoginParam")
public class UserLoginParam implements Serializable {

    @NotBlank(message = "Email can not be blank")
    @Length(max = 32, message = "Email length must less than 32")
    @Email(message = "email format error")
    @Schema(description = "Email", requiredMode = Schema.RequiredMode.REQUIRED, example = "evan@love.com")
    private String email;

    @NotBlank(message = "Password can not be blank")
    @Length(min = 8, max = 64, message = "Password length must be between 8 and 64")
    @Schema(description = "Password", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
