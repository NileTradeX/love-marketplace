package com.love.merchant.backend.model.param;

import com.love.common.param.PageParam;
import com.love.review.enums.ReviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "ReviewQueryPageParam")
public class ReviewQueryPageParam extends PageParam {

    @NotNull(message = "Related type can't be null")
    private Integer type = ReviewType.GOODS.getType();

    @NotNull(message = "related id can't be null")
    private Long relatedId;
}
