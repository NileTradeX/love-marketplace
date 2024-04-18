package com.love.backend.model.param;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class InfOrderQueryPageParam implements Serializable {
    private Long influencerId;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
}
