package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class BoltShipToStoreOptionDTO {
    @SerializedName("service")
    private String service;
    @SerializedName("reference")
    private String reference;
    @SerializedName("signature")
    private String signature;
    @SerializedName("cost")
    private BigDecimal cost;
    @SerializedName("tax_amount")
    private BigDecimal taxAmount;
    @SerializedName("store_name")
    private String storeName;
    @SerializedName("address")
    private BoltAddressDTO address;
    @SerializedName("distance")
    private Integer distance;
    @SerializedName("distance_unit")
    private String distanceUnit;
}
