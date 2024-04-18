package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(type = "RefundItemParam")
public class RefundItemParam implements Serializable {
    @Schema(description = "goodsId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "goodsId can't be null")
    private Long goodsId;

    @Schema(description = "skuId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "skuId can't be null")
    private Long skuId;

    @Schema(description = "qty", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "qty can't be null")
    private Integer qty;

    @Schema(description = "price", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "price can't be null")
    private BigDecimal price;

    @Schema(description = "skuImg", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "skuImg can't be blank")
    private String skuImg;

    @Schema(hidden = true)
    private String skuInfo;
}