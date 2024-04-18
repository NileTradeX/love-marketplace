package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(title = "BrandSaveParam")
public class BrandSaveParam implements Serializable {

    private Long id;

    @Schema(hidden = true)
    private Long userId;

    @NotBlank(message = "Brand name can't be empty")
    private String name;

    @NotBlank(message = "Brand logo can't be empty")
    private String logo;
}
