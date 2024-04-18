package com.love.common.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "ByUserIdParam")
public class ByUserIdParam implements Serializable {
    private Long id;
    private Long userId;
}
