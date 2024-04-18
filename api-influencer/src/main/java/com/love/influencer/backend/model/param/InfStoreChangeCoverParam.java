package com.love.influencer.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(title = "StoreChangeCoverParam")
public class InfStoreChangeCoverParam implements Serializable {

    @Schema(description = "influencerId", example = "1", hidden = true)
    private Long influencerId;

    @NotBlank(message = "Cover can not be null")
    @Schema(description = "Cover", example = "*.jpg")
    private String cover;

}
