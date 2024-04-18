package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "MerUserChangePasswordParam")
public class MerUserChangePasswordParam implements Serializable {

    @NotNull(message = "Id can not be null")
    @Schema(description = "user id", example = "1")
    private Long id;

    @NotBlank(message = "password can not be null")
    @Length(max = 64, message = "password length must less than 64")
    @Schema(description = "Password", example = "P@assw0rd123")
    private String password;
}
