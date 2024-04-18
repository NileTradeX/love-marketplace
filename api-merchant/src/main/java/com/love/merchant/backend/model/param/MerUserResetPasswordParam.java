package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(title = "MerUserResetPasswordParam")
public class MerUserResetPasswordParam implements Serializable {

    @NotBlank(message = "id can not be null")
    @Schema(description = "id", example = "1")
    private String id;

    @NotBlank(message = "Password can not be null")
    @Length(max = 64, min = 8, message = "Password length must between 8 and 64")
    @Schema(description = "Password", example = "P@ssword123")
    private String password;
}
