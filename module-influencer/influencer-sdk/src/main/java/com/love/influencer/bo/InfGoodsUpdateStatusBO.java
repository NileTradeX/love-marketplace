package com.love.influencer.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class InfGoodsUpdateStatusBO implements Serializable {
    private Long influencerId;
    private List<Long> ids;
    private Integer status;
}

