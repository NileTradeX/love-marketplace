package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsReviewPageQueryBO implements Serializable {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private Integer reviewStatus;
}
