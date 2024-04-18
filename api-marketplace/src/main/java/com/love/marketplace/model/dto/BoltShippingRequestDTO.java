package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BoltShippingRequestDTO extends BoltEventDTO {
    @SerializedName("order_token")
    private String orderToken;
    @SerializedName("shipping_address")
    private BoltAddressDTO shippingAddress;
    @SerializedName("cart")
    private BoltCartDTO cart;
    @SerializedName("request_source")
    private String requestSource;
}
