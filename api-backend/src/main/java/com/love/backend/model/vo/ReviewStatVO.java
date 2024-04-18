package com.love.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewStatVO implements Serializable {

    @Schema(description = "status", example = "0")
    private Integer status;

    @Schema(description = "name", example = "All")
    private String name;

    @Schema(description = "review number", example = "0")
    private Long number;
}
