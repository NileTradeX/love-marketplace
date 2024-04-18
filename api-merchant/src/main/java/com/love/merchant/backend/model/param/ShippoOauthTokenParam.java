package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ShippoOauthTokenParam implements Serializable {

    @Schema(hidden = true)
    private Long userId;

    @NotBlank(message = "Authorization Code can't be blank")
    private String code;
}
