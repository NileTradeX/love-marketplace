package com.love.influencer.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class InfStoreSaveParam implements Serializable {

    @NotNull(message = "influencerId can not be null")
    @Schema(description = "userId", example = "1", hidden = true)
    private Long userId;
    private String title;
    private String cover;
    @NotBlank(message = "displayName can not be null")
    @Schema(description = "displayName", example = "test")
    private String displayName;
    private String description;
    private Integer goodsSortType;
}
