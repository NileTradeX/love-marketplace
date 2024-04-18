package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
public class BoltAmountDTO extends BoltCurrencyDTO {
    @SerializedName("amount")
    private Integer amount;
    public BoltAmountDTO(Integer amount){
        this.amount = amount;
        super.setCurrency("USD");
        super.setCurrencySymbol("$");
    }
}
