package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ShoppingCartGoodsQueryListParam implements Serializable {
    @NotNull(message = "userId cannot be null")
    @Schema(hidden = true)
    private Long userId;

    @Schema(description = "goodsIdSKuIdStr", requiredMode = Schema.RequiredMode.AUTO, example = "123_456,789_145")
    private String goodsIdSKuIdStr;
}
