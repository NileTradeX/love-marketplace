package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BoltDiscountDTO {
    @SerializedName("amount")
    private BoltAmountDTO amount;
    @SerializedName("code")
    private String code;
    @SerializedName("reference")
    private String reference;
    @SerializedName("description")
    private String description;
    @SerializedName("details_url")
    private String detailsUrl;
    @SerializedName("free_shipping")
    private FreeShippingDTO freeShipping;
    @SerializedName("discount_category")
    private String discountCategory;

    @NoArgsConstructor
    @Data
    public static class FreeShippingDTO {
        @SerializedName("is_free_shipping")
        private Boolean isFreeShipping;
        @SerializedName("maximum_cost_allowed")
        private Integer maximumCostAllowed;
    }
}
