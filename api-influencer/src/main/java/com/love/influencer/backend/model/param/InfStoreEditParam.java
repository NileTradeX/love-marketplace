package com.love.influencer.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class InfStoreEditParam implements Serializable {

    @NotNull(message = "id can not be null")
    @Schema(description = "id", example = "1")
    private Long id;

    @NotNull(message = "influencerId can not be null")
    @Schema(description = "influencerId", example = "1")
    private Long influencerId;

    private String title;

    private String cover;

    private String displayName;

    private String description;

    private Integer goodsSortType;
}
