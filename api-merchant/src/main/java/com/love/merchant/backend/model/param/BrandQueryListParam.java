package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "BrandQueryListParam")
public class BrandQueryListParam implements Serializable {

    @Schema(description = "Merchant id", hidden = true)
    private Long userId;
}
