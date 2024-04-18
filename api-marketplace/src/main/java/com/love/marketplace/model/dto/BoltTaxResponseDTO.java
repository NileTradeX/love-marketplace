package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BoltTaxResponseDTO extends BoltEventDTO {
    @SerializedName("tax_result")
    private TaxResultDTO taxResult = new TaxResultDTO();
    @SerializedName("shipping_option")
    private BoltShippingOptionDTO shippingOption;
    @SerializedName("pickup_option")
    private BoltPickupOptionDTO pickupOption;
    @SerializedName("ship_to_store_option")
    private BoltShipToStoreOptionDTO shipToStoreOption;
    @SerializedName("items")
    private List<BoltItemDTO> items;
    @NoArgsConstructor
    @Data
    public static class TaxResultDTO {
        @SerializedName("items")
        private List<BoltItemDTO> items;
        /**
         * The rate subtotal in cents.
         */
        @SerializedName("rate_subtotal")
        private Integer rateSubtotal = 0;
        /**
         * The shipping rate amount in cents.
         */
        @SerializedName("rate_shipping")
        private Integer rateShipping = 0;
        /**
         * The amount in cents. Nullable for Transactions Details.
         */
        @SerializedName("subtotal_amount")
        private Integer subtotalAmount = 0;
    }
    public BoltTaxResponseDTO(List<BoltItemDTO> items){
        this.taxResult.setItems(items);
        this.items = items;
    }
}
