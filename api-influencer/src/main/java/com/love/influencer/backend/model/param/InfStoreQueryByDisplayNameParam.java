package com.love.influencer.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "InfluencerStoreQueryParam")
public class InfStoreQueryByDisplayNameParam implements Serializable {

    @Schema(description = "displayName", example = "test")
    private String displayName;
}
