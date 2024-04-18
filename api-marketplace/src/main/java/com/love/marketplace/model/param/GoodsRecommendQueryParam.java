package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(title = "GoodsRecommendQueryParam")
public class GoodsRecommendQueryParam implements Serializable {

    @Schema(description = "first category ids")
    private List<Long> firstCateIds;

    @Schema(description = "second category ids")
    private List<Long> secondCateIds;

    @Schema(description = "current goods Ids")
    private List<Long> currentGoodsIds;

    @Schema(description = "Number of recommendations")
    private Integer recommendGoodsSize;
}
