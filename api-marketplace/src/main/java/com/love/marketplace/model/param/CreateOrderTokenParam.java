package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Schema(type = "CreateOrderTokenParam")
public class CreateOrderTokenParam implements Serializable {

    @Schema(description = "items", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "items cannot be null")
    private List<ItemsParam> items;

    @Schema(hidden = true)
    private Long userId;

}
