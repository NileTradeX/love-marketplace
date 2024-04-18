package com.love.influencer.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "InfluencerChangeAvatarParam")
public class InfUserChangeAvatarParam implements Serializable {

    @NotNull(message = "id can not be null")
    @Schema(description = "id", example = "1", hidden = true)
    private Long id;

    @NotBlank(message = "Avatar can not be null")
    @Schema(description = "Avatar", example = "*.jpg")
    private String avatar;

}
