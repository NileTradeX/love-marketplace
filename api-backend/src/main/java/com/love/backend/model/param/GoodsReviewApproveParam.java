package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "GoodsReviewApproveParam")
public class GoodsReviewApproveParam implements Serializable {

    @NotNull(message = "the goods id can't be null")
    @Schema(description = "goods id", requiredMode = REQUIRED)
    private Long id;

    @NotNull(message = "score can't be null")
    @Schema(description = "love score", requiredMode = REQUIRED)
    @Range(min = 1, max = 100, message = "score must between 1 and 100")
    private Integer loveScore;

    @NotBlank(message = "why we love should not be null")
    @Schema(description = "why we love", requiredMode = REQUIRED)
    private String whyLove;
}
