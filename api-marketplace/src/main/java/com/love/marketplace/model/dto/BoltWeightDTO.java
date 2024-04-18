package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoltWeightDTO {
    @SerializedName("weight")
    private Integer weight;
    @SerializedName("unit")
    private String unit;
}
