package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class BoltCartDTO {
    @SerializedName("total_amount")
    private Integer totalAmount;
    @SerializedName("items")
    private List<BoltItemDTO> items;
    @SerializedName("discounts")
    private List<BoltDiscountDTO> discounts;
    @SerializedName("order_reference")
    private String orderReference;
    @SerializedName("display_id")
    private String displayId;
    @SerializedName("cart_url")
    private String cartUrl;
    @SerializedName("currency")
    private String currency;
    @SerializedName("metadata")
    private BoltMetadataDTO metadata;
}
