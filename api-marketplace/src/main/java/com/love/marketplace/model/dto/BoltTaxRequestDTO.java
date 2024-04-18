package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BoltTaxRequestDTO extends BoltEventDTO {
    @SerializedName("shipping_address")
    private BoltAddressDTO shippingAddress;
    @SerializedName("cart")
    private BoltCartDTO cart;
    @SerializedName("request_source")
    private String requestSource;
    @SerializedName("shipping_option")
    private BoltShippingOptionDTO shippingOption;
    @SerializedName("pickup_option")
    private BoltPickupOptionDTO pickupOption;
    @SerializedName("ship_to_store_option")
    private BoltShipToStoreOptionDTO shipToStoreOption;
    @SerializedName("metadata")
    private BoltMetadataDTO metadata;
}
