package com.love.merchant.backend.model.param;

import com.love.merchant.bo.ShippingSettingBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;


@Data
@Schema(title = "MerShippingTemplateParam")
public class MerShippingTemplateParam implements Serializable {

    @NotNull(message = "User id can not be null")
    @Schema(description = "user id", example = "1", hidden = true)
    private Long userId;

    @NotNull(message = "Shipping models can't be empty")
    @Schema(description = "Shipping Models", example = "1")
    private Integer shippingModels;

    @Schema(description = "Shipping template setting", requiredMode = AUTO)
    private ShippingSettingBO setting;
}
