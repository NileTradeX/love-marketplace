package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(description = "TokenParam")
public class TokenParam implements Serializable {

    @Schema(description = "verify token", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "token cannot be null")
    private String token;
}
