package com.love.marketplace.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfluencerStoreVO implements Serializable {
    private Long id;
    private Long influencerId;
    private String title;
    private String cover;
    private String displayName;
    private String description;
    private Integer goodsSortType;
}
