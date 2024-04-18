package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "ReviewRejectParam")
public class ReviewRejectParam implements Serializable {

    @Schema(description = "id")
    @NotNull(message = "Review id can't be null")
    private Long id;

    @Schema(description = "comment")
    @NotBlank(message = "Reject comment can't be blank")
    private String comment;
}
