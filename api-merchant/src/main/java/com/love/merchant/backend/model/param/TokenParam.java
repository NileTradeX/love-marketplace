package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(title = "TokenParam")
public class TokenParam implements Serializable {

    @Schema(description = "token", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "token is required")
    private String token;
}
