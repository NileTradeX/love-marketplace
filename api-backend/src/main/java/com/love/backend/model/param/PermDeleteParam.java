package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PermDeleteParam implements Serializable {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Id can not be null")
    private Long id;


    @NotNull(message = "flag cannot be null")
    @Schema(description = "menu flag > 1:admin platform 2:merchant platform", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer flag;
}
