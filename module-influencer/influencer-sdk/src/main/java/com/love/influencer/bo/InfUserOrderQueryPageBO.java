package com.love.influencer.bo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class InfUserOrderQueryPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private Long influencerId;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
}
