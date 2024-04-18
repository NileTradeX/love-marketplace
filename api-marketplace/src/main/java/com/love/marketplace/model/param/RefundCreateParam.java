package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(type = "RefundCreateParam")
public class RefundCreateParam implements Serializable {
    @Schema(description = "orderNo", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "orderNo cannot be blank")
    private String orderNo;

    @Schema(description = "merOrderNo", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "merOrderNo cannot be blank")
    private String merOrderNo;

    @Schema(description = "merchantId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "merchantId cannot be null")
    private Long merchantId;

    @Schema(description = "brandId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "brandId cannot be null")
    private Long brandId;

    @Schema(hidden = true)
    private Long userId;

    @Schema(description = "the total amount for the refund goods", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "saleAmount cannot be null")
    private BigDecimal saleAmount;

    @Schema(description = "afterSaleType 1:Refund Only 2:Return and Refund", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "afterSaleType cannot be null")
    private Integer afterSaleType;

    @Schema(description = "afterSaleReason", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "afterSaleReason cannot be blank")
    private String afterSaleReason;

    @Schema(description = "the total refund amount for the refund goods", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "refundAmount cannot be null")
    private BigDecimal refundAmount;

    @Schema(description = "the total shippingFee for the refund goods", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "shippingFee cannot be null")
    private BigDecimal shippingFee;

    @Schema(description = "the details for the refund goods", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "skuBOList cannot be null")
    @Valid
    private List<RefundItemParam> skuBOList;
}
