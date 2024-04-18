package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserIdParam implements Serializable {

    @Schema(hidden = true)
    private Long userId;
}
