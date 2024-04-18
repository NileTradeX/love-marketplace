package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;

@Data
@Schema(title = "CategoryQueryPageParam")
public class CategoryQueryByPidParam implements Serializable {

    @Schema(description = "pid for category", requiredMode = AUTO, example = "0")
    private Integer pid = 0;
}
