package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BoltOrderCreateResponseDTO extends BoltEventDTO {
    @SerializedName("display_id")
    private String displayId;
    @SerializedName("order_received_url")
    private String orderReceivedUrl;
}
