package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "OrderSimpleStatParam")
public class OrderSimpleStatParam implements Serializable {
    @Schema(description = "merchantId", hidden = true)
    private Long userId;
}
