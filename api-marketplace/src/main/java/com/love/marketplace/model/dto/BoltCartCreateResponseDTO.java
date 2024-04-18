package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BoltCartCreateResponseDTO extends BoltEventDTO {
    @SerializedName("cart")
    private BoltCartDTO cart;
}
