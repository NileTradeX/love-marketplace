package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@Schema(title = "SysUserLoginParam")
public class SysUserLoginParam implements Serializable {

    @Schema(description = "account", requiredMode = Schema.RequiredMode.REQUIRED, example = "ruoran")
    @Length(max = 32, message = "User account length must less than 32")
    private String account;

    @Schema(description = "password", requiredMode = Schema.RequiredMode.REQUIRED, example = "evan chen")
    @Length(min = 8, max = 32, message = "Password length must between 8 and 32")
    private String password;

}
