package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@Data
public class BoltPickupOptionDTO {
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
    @SerializedName("pickup_window_open")
    private Instant pickupWindowOpen;
    @SerializedName("pickup_window_close")
    private Instant pickupWindowClose;
    @SerializedName("address")
    private BoltAddressDTO address;
    @SerializedName("distance")
    private Integer distance;
    @SerializedName("distance_unit")
    private String distanceUnit;
}
