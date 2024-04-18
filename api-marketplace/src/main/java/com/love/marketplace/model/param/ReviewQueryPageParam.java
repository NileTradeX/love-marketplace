package com.love.marketplace.model.param;

import com.love.common.param.PageParam;
import com.love.review.enums.ReviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@Schema(title = "ReviewQueryPageParam")
@EqualsAndHashCode(callSuper = true)
public class ReviewQueryPageParam extends PageParam {

    @Schema(description = "type > 1:goods", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Related type can't be null")
    private Integer type = ReviewType.GOODS.getType();

    @Schema(description = "relatedId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Related id can't be null")
    private Long relatedId;

    @Schema(description = "relatedStr", requiredMode = Schema.RequiredMode.AUTO)
    private String relatedStr;

    @Schema(description = "userId", hidden = true)
    private Long userId;

    @Schema(description = "from > 0:pdp 1:orp")
    @NotNull(message = "from can't be null")
    private Integer from;
}
