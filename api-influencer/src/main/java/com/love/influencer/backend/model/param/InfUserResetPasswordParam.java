package com.love.influencer.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class InfUserResetPasswordParam implements Serializable {

    @NotNull(message = "id can not be null")
    @Schema(description = "id", example = "1")
    private Long id;

    @NotBlank(message = "password can not be null")
    @Schema(description = "password", example = "12345678")
    private String password;
}
