package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(type = "OrderCreateParam")
public class OrderCreateParam implements Serializable {

    @Schema(description = "Shipment Template Id", requiredMode = Schema.RequiredMode.AUTO)
    private Long shippingRatesTemplateId;

    @Schema(description = "email", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "email cannot be null")
    @Email(message = "email format error")
    private String email;

    @Schema(description = "address id", requiredMode = Schema.RequiredMode.AUTO)
    private Long addrId;

    @Schema(description = "goods id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "goods id cannot be null")
    private Long goodsId;

    @Schema(description = "sku id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "sku id cannot be null")
    private Long skuId;

    @Schema(description = "qty", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "qty cannot be null")
    @Min(value = 1, message = "qty at least is 1")
    private Integer qty;

    @Schema(hidden = true)
    private Long userId;

    @Schema(description = "user reg info", requiredMode = Schema.RequiredMode.AUTO)
    private UserRegisterParam userRegisterParam;

    @Schema(description = "user address param", requiredMode = Schema.RequiredMode.AUTO)
    private UserAddressSaveParam userAddressSaveParam;

    @Schema(description = "Influencer Code", example = "test")
    private String influencerCode;
}
