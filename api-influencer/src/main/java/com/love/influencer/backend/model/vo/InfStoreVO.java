package com.love.influencer.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfStoreVO implements Serializable {
    private Long id;
    private Long influencerId;
    private String influencerCode;
    private String title;
    private String cover;
    private String displayName;
    private String description;
    private Integer goodsSortType;
}
