package com.love.merchant.backend.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@EqualsAndHashCode(callSuper = true)
public class LabelQueryPageParam extends PageParam {

    @NotNull(message = "Label type can't be null")
    @Schema(description = "Label type 0: ingredient, 1: benefit", requiredMode = REQUIRED, example = "0")
    private Integer type;

    private Integer status;
}
