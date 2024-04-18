package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(title = "OrderRefundDeclineParam")
public class OrderRefundDeclineParam implements Serializable {
    @Schema(description = "merchantDealDesc", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "merchantDealDesc cannot be blank")
    private String merchantDealDesc;

    @Schema(description = "afterSaleNo", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "afterSaleNo cannot be blank")
    private String afterSaleNo;

    @Schema(hidden = true)
    private Long userId;
}
