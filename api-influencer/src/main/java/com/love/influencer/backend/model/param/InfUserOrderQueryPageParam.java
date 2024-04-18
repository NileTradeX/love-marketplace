package com.love.influencer.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InfUserOrderQueryPageParam {
    private int pageNum = 1;
    private int pageSize = 10;
    @Schema(hidden = true)
    private Long userId;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
}
