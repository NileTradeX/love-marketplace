package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BoltEventRequestDTO<T extends BoltEventDTO> {
    @SerializedName("event")
    private String event;
    @SerializedName("data")
    private T data;
}
