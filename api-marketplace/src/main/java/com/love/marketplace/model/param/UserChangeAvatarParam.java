package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(title = "UserChangeHeadImgParam")
public class UserChangeAvatarParam implements Serializable {

    @Schema(description = "user id", example = "1", hidden = true)
    private Long userId;

    @NotBlank(message = "Avatar can not be null")
    @Schema(description = "Avatar", example = "*.jpg")
    private String avatar;

}
