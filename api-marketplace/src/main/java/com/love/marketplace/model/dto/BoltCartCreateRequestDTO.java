package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BoltCartCreateRequestDTO extends BoltEventDTO {
    @SerializedName("items")
    private List<BoltItemDTO> items;
    @SerializedName("discounts")
    private List<BoltDiscountDTO> discounts;
    @SerializedName("currency")
    private String currency;
    @SerializedName("metadata")
    private BoltMetadataDTO metadata;
}
