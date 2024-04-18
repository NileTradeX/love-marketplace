package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(type = "ShoppingCartGoodsMergeParam")
public class ShoppingCartGoodsMergeParam implements Serializable {

    @NotNull(message = "userId cannot be null")
    @Schema(hidden = true)
    private Long userId;

    @NotNull(message = "itemList cannot be null")
    @Schema(description = "itemList", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Item> itemList;
    @Data
    public static class Item{
        @NotNull(message = "goodsId cannot be null")
        @Schema(description = "goodsId", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long goodsId;

        @NotNull(message = "skuId cannot be null")
        @Schema(description = "skuId", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long skuId;

        @Schema(description = "price", requiredMode = Schema.RequiredMode.REQUIRED)
        private BigDecimal price;

        @NotNull(message = "qty cannot be null")
        @Schema(description = "qty", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer qty;

        @Schema(description = "influencerCode", requiredMode = Schema.RequiredMode.AUTO)
        private String influencerCode;
    }
}
