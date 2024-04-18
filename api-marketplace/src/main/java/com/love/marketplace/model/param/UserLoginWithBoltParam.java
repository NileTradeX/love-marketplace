package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Tony
 * 2023/4/26
 */
@Data
@Schema(title = "UserLoginWithBoltParam")
public class UserLoginWithBoltParam implements Serializable {

    @NotBlank(message = "Bolt code can not be blank")
    @Schema(description = "Bolt code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank(message = "Bolt scope can not be blank")
    @Schema(description = "Bolt scope", requiredMode = Schema.RequiredMode.REQUIRED)
    private String scope;
}
