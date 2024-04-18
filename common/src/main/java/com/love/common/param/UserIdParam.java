package com.love.common.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "IdParam")
public class UserIdParam implements Serializable {
    @Schema(hidden = true)
    private Long userId;
}
