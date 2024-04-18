package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(type = "ItemsParam")
public class ItemsParam implements Serializable {

    @Schema(description = "goods id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "goods id cannot be null")
    private Long goodsId;

    @Schema(description = "sku id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "sku id cannot be null")
    private Long skuId;

    @Schema(description = "unit Price")
    private Integer unitPrice;

    @Schema(description = "qty", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "qty cannot be null")
    @Min(value = 1, message = "qty at least is 1")
    private Integer qty;

    @Schema(description = "influencer code")
    private String influencerCode;
}
