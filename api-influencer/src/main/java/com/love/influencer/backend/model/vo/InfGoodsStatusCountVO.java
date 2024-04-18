package com.love.influencer.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfGoodsStatusCountVO implements Serializable {
    private Long toBeRecommended;
    private Long recommendedCount;
    private Long invalidCount;
    private Long totalCount;
}
