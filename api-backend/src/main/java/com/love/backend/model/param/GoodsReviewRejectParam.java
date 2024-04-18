package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "GoodsReviewRejectParam")
public class GoodsReviewRejectParam implements Serializable {

    @NotEmpty(message = "the goods id list can't be null")
    @Schema(description = "goods id list", requiredMode = REQUIRED)
    private List<Long> ids;

    @NotBlank(message = "the reject comment can't be null")
    @Schema(description = "Reject comment", requiredMode = REQUIRED, example = "comment")
    private String comment;
}
