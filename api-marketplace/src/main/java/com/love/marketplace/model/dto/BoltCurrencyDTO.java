package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BoltCurrencyDTO {
    @SerializedName("currency")
    private String currency;
    @SerializedName("currency_symbol")
    private String currencySymbol;
}
