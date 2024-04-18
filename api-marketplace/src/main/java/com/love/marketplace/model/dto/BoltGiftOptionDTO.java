package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BoltGiftOptionDTO {
    @SerializedName("wrap")
    private Boolean wrap;
    @SerializedName("message")
    private String message;
    @SerializedName("cost")
    private Integer cost;
    @SerializedName("merchant_product_id")
    private String merchantProductId;
}
