package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsReviewApproveBO implements Serializable {
    private Long id;
    private Integer loveScore;
    private String whyLove;
}
