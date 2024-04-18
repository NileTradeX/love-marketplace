package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserAddressSetDefaultParam implements Serializable {

    @NotNull(message = "id cannot be null")
    private Long id;

    @NotNull(message = "User id can't be null")
    @Schema(description = "userId", requiredMode = Schema.RequiredMode.REQUIRED, example = "1", hidden = true)
    private Long userId;
}
