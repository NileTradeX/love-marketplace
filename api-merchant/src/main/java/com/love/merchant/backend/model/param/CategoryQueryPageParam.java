package com.love.merchant.backend.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "CategoryQueryPageParam")
public class CategoryQueryPageParam extends PageParam {
    @Schema(description = "level", requiredMode = AUTO, example = "1")
    private Integer level = 1;

    @Schema(description = "type", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "category type can not be null")
    private Integer type;
}
