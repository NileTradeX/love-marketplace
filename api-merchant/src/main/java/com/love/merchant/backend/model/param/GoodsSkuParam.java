package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "GoodsSkuParam")
public class GoodsSkuParam implements Serializable {

    @Schema(description = "sku id", requiredMode = AUTO, example = "id")
    private Long id;

    @Schema(description = "cover image", requiredMode = AUTO, example = "cover")
    private String cover;

    @Schema(description = "image list(Comma split str)", requiredMode = AUTO, example = "key1,key2,key3")
    private String imgList;

    @Schema(description = "Attribute values (format is Large/black)", requiredMode = REQUIRED, example = "Large/black")
    @NotBlank(message = "Attribute values can't be blank")
    private String attrValues;

    private String attrValueJson;

    private Long goodsId;

    @Schema(description = "Sku price", requiredMode = REQUIRED, example = "00.0")
    @NotNull(message = "Sku price can't be null")
    private BigDecimal price;

    @Schema(description = "On hand stock", requiredMode = REQUIRED, example = "0")
    @NotNull(message = "On hand stock can't be null")
    private Integer onHandStock;

    @Schema(description = "Available stock", requiredMode = REQUIRED, example = "0")
    @NotNull(message = "Available stock can't be null")
    private Integer availableStock;

    @Schema(description = "Sku code(User input)", requiredMode = AUTO, example = "abc")
    private String code;

    @Schema(description = "Sku status(0: disable, 1: enabled)", requiredMode = REQUIRED, example = "0")
    @NotNull(message = "sku status can't be null")
    private Integer status;

    @Schema(description = "Shipping weight", requiredMode = REQUIRED, example = "00.0")
    @NotNull(message = "Shipping weight can't be null")
    private BigDecimal shippingWeight;

    @Schema(description = "Shipping weight unit", requiredMode = REQUIRED, example = "lb")
    @NotBlank(message = "Shipping weight unit can't be null")
    private String shippingWeightUnit;

    @Schema(description = "Gtin", requiredMode = AUTO, example = "12345678901234")
    @Size(max = 16)
    private String gtin;

    @Schema(description = "Mpn", requiredMode = AUTO, example = "MGC83CH/A")
    @Size(max = 255)
    private String mpn;
}
