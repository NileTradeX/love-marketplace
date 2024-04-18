package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MerShippingTemplateQueryParam implements Serializable {

    @NotNull(message = "User id can not be null")
    @Schema(description = "user id", example = "1", hidden = true)
    private Long userId;
}
