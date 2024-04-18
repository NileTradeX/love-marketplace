package com.love.goods.bo;

import lombok.Data;

import java.util.List;

@Data
public class GoodsRecommendQueryBO {
    private List<Long> currentGoodsIds;
    private List<Long> secondCateIds;
    private List<Long> firstCateIds;
    private Integer recommendGoodsSize;
}
