package com.love.backend.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "GoodsReviewPageParam")
public class GoodsReviewPageParam extends PageParam {

    @Schema(description = "review status (null: All, 0: Awaiting, 1: Approved, 2: Rejected)", requiredMode = REQUIRED, example = "0")
    private Integer reviewStatus;
}
