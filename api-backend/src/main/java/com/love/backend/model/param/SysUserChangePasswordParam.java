package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "SysUserChangePasswordParam")
public class SysUserChangePasswordParam implements Serializable {

    @NotNull(message = "Id can not be null")
    @Schema(description = "id", requiredMode = REQUIRED, example = "1")
    private Long id;

    @Schema(description = "password", requiredMode = REQUIRED, example = "66668888", defaultValue = "66668888")
    @Length(max = 32, min = 8, message = "Password length must between 8 and 32")
    private String password;
}
