package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class AfterSalesQueryParam implements Serializable {
    @NotBlank(message = "afterSaleNo can't be blank")
    @Schema(description = "afterSaleNo", requiredMode = REQUIRED)
    private String afterSaleNo;
}
