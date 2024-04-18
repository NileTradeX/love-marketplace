package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(type = "RefundCloseParam")
public class RefundCloseParam implements Serializable {
    @Schema(description = "afterSaleNo", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "afterSaleNo cannot be blank")
    private String afterSaleNo;
}
