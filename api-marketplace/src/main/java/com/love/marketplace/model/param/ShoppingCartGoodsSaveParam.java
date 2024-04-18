package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(type = "ShoppingCartGoodsBatchSaveParam")
public class ShoppingCartGoodsSaveParam implements Serializable {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.AUTO)
    private Long id;

    @NotNull(message = "userId cannot be null")
    @Schema(hidden = true)
    private Long userId;

    @NotNull(message = "goodsId cannot be null")
    @Schema(description = "goodsId", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long goodsId;

    @NotNull(message = "skuId cannot be null")
    @Schema(description = "skuId", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long skuId;

    @NotNull(message = "price cannot be null")
    @Schema(description = "price", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    @NotNull(message = "qty cannot be null")
    @Schema(description = "qty", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer qty;

    @Schema(description = "shippingTemplateId", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long shippingTemplateId;

    @Schema(description = "influencerCode", requiredMode = Schema.RequiredMode.AUTO)
    private String influencerCode;
}
