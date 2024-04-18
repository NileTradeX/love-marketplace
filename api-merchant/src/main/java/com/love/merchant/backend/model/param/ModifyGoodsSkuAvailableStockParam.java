package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "ModifyGoodsSkuStockParam")
public class ModifyGoodsSkuAvailableStockParam implements Serializable {


    private List<ModifySkuAvailableStockParam> modifySkuAvailableStockList;

    @Data
    @Valid
    public static class ModifySkuAvailableStockParam implements Serializable {
        @NotNull(message = "the sku id can't be null")
        @Schema(description = "sku id", requiredMode = REQUIRED, example = "0")
        private Long skuId;

        @NotNull(message = "the available stock can't be null")
        @Min(value = 1, message = "available stock must be greater than 1")
        @Schema(description = "available stock", requiredMode = REQUIRED, example = "1")
        private Integer availableStock;
    }

}
