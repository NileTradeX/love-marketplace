package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayInfoCreateParam implements Serializable {
    @Schema(description = "order no", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "order no can not be blank")
    private String orderNo;
    @Schema(description = "total amount", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "total amount can not be null")
    private Long totalAmount;
    @Schema(description = "app fee", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "app fee can not be null")
    private Long appFee;
    @Schema(description = "channel 0 stripe 1 adyen", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "user type can not be null")
    private Integer channel;
    @Schema(description = "payment id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "payment id can not be blank")
    private String paymentId;
}
