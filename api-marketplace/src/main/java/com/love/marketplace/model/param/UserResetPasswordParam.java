package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(title = "UserResetPasswordParam")
public class UserResetPasswordParam implements Serializable {

    @NotBlank(message = "Token can not be null")
    @Schema(description = "Token", example = "25d55ad283aa400af464c76d713c07ad")
    private String token;

    @NotBlank(message = "Password can not be null")
    @Length(max = 64, min = 8, message = "Password length must between 8 and 64")
    @Schema(description = "Password", example = "P@ssword123")
    private String password;

}

