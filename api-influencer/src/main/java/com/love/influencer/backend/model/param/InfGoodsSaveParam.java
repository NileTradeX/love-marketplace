package com.love.influencer.backend.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InfGoodsSaveParam implements Serializable {
    @NotNull(message = "goods Id can not be null")
    private Long goodsId;
    @NotNull(message = "sales Volume can not be null")
    private Integer salesVolume;
    @NotNull(message = "min Price can not be null")
    private BigDecimal minPrice;
    @NotNull(message = "max Price can not be null")
    private BigDecimal maxPrice;
    @NotNull(message = "community Score can not be null")
    private Integer communityScore;
    @NotNull(message = "goods status can not be null")
    private Integer goodsStatus;
    @NotNull(message = "available stock can not be null")
    private Integer availableStock;
}

