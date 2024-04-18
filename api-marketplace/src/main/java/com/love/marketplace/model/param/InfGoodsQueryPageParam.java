package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "InfluencerGoodsQueryPageParam")
public class InfGoodsQueryPageParam implements Serializable {

    private int pageNum = 1;
    private int pageSize = 10;

    @NotNull(message = "influencerId can't be null")
    @Schema(description = "influencerId", requiredMode = REQUIRED)
    private Long influencerId;

    private Integer goodsSortType;

    private Integer status;
}
