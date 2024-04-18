package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BoltShipmentDTO {
    @SerializedName("shipping_address_id")
    private String shippingAddressId;
    @SerializedName("shipping_address")
    private BoltAddressDTO shippingAddress;
    @SerializedName("total_weight")
    private Integer totalWeight;
    @SerializedName("shipping_method")
    private String shippingMethod;
    @SerializedName("carrier")
    private String carrier;
    @SerializedName("service")
    private String service;
    @SerializedName("expedited")
    private Boolean expedited;
    @SerializedName("cost")
    private Integer cost;
    @SerializedName("tax_amount")
    private Integer taxAmount;
    @SerializedName("package_type")
    private String packageType;
    @SerializedName("package_width")
    private Integer packageWidth;
    @SerializedName("package_height")
    private Integer packageHeight;
    @SerializedName("package_depth")
    private Integer packageDepth;
    @SerializedName("package_dimension_unit")
    private String packageDimensionUnit;
    @SerializedName("tax_code")
    private String taxCode;
    @SerializedName("estimated_delivery_date")
    private String estimatedDeliveryDate;
    @SerializedName("type")
    private String type;
    @SerializedName("discount_by_membership")
    private Boolean discountByMembership;
}
