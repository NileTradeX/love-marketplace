package com.love.influencer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfGoodsStatusCountDTO implements Serializable {
    private Long toBeRecommended;
    private Long recommendedCount;
    private Long invalidCount;
    private Long totalCount;
}
