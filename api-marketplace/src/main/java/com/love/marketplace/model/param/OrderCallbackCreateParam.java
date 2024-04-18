package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(type = "OrderGuestCreateParam")
public class OrderCallbackCreateParam implements Serializable {

    @Schema(description = "user id")
    private Long userId;

    @Schema(description = "email")
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email format error")
    private String email;

    @Schema(description = "order no", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "order no cannot be blank")
    private String orderNo;

    @Schema(description = "items", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "total amount cannot be null")
    List<ItemsParam> items;

    @Schema(description = "total amount", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "total amount cannot be null")
    private BigDecimal totalAmount;

    @Schema(description = "app fee", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "app fee cannot be null")
    private BigDecimal appFee;

    @Schema(description = "shipping fee", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "shipping fee cannot be null")
    private BigDecimal shippingFee;
;
    @Schema(description = "tax fee", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "tax fee cannot be null")
    private BigDecimal taxFee;

    @Schema(description = "user address param", requiredMode = Schema.RequiredMode.REQUIRED)
    private UserAddressSaveParam userAddressSaveParam;
}
