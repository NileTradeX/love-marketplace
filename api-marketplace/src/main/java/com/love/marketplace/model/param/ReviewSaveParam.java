package com.love.marketplace.model.param;

import com.love.review.enums.ReviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "ReviewSaveParam")
public class ReviewSaveParam implements Serializable {

    @Schema(description = "pid", requiredMode = Schema.RequiredMode.AUTO)
    private Long pid;

    @Schema(description = "title", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "content", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Review content can't be blank")
    private String content;

    @Schema(description = "rating", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "rating can't be null")
    private Integer rating;

    @Schema(description = "Merchant", hidden = true)
    private Long merchantId;

    @Schema(description = "userId", hidden = true)
    private Long userId;

    @Schema(description = "type", requiredMode = Schema.RequiredMode.AUTO)
    private Integer type = ReviewType.GOODS.getType();

    @Schema(description = "relatedId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Related id can't be null")
    private Long relatedId;

    @NotBlank(message = "RelatedStr can't be null")
    @Schema(description = "relatedStr", requiredMode = Schema.RequiredMode.REQUIRED)
    private String relatedStr;
}
