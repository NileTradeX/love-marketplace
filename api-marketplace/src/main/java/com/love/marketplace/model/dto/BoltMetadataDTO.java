package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BoltMetadataDTO {
    @SerializedName("influencer_code")
    private String influencerCode;
    @SerializedName("user_id")
    private String userId;
}
