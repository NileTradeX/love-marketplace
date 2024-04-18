package com.love.marketplace.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class BoltOrderTokenVO implements Serializable {
    @Schema(description = "Order Token.", example = "ffc07a6806cdff29b16077f54b4fd2742f893276c3a6a15d13f4ffb2cb628420")
    private String orderToken;
    @Schema(description = "Order Reference. This is LOVE's order number is mapping to bolt order reference.", example = "5669616782533097808")
    private String orderReference;
}
