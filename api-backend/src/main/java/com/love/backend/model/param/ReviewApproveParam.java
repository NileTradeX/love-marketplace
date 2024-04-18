package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "ReviewApproveParam")
public class ReviewApproveParam implements Serializable {

    @NotNull(message = "Review id can't be null")
    private Long id;
}
