package com.love.influencer.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfGoodsQueryPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private Long influencerId;
    private Integer goodsSortType;
    private Integer status;
}
