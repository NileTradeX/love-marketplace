package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(title = "OrderRefundAgreeParam")
public class OrderRefundAgreeParam implements Serializable {
    @NotBlank(message = "orderNo cannot be blank")
    @Schema(description = "orderNo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String orderNo;

    @NotBlank(message = "afterSaleNo cannot be blank")
    @Schema(description = "afterSaleNo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String afterSaleNo;

    @Schema(hidden = true)
    private Long userId;
}
