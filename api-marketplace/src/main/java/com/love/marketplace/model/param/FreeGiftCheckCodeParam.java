package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(title = "FreeGiftCheckCodeParam")
public class FreeGiftCheckCodeParam implements Serializable {

    @Schema(hidden = true)
    private Long userId;

    @NotBlank(message = "code can not be null")
    @Schema(description = "code")
    private String code;

}
