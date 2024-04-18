package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(title = "GoodsPriceRangeParam")
public class GoodsPriceRangeParam implements Serializable {

    @Schema(description = "min price")
    @NotNull(message = "min price cannot be null")
    private BigDecimal min;

    @Schema(description = "max price")
    @NotNull(message = "max price cannot be null")
    private BigDecimal max;

}
