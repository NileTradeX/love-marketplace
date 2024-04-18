package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "UserAddressQueryListParam")
public class UserAddressQueryListParam implements Serializable {

    @NotNull(message = "User id can't be null")
    @Schema(description = "userId", requiredMode = Schema.RequiredMode.REQUIRED, example = "1", hidden = true)
    private Long userId;
}
