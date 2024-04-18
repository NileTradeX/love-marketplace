package com.love.influencer.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class InfGoodsBatchSaveBO implements Serializable {
    private List<InfGoodsSaveBO> selectedGoods;
    private Integer status;
    private Long influencerId;
}
