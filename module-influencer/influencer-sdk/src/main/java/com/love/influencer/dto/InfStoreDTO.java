package com.love.influencer.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfStoreDTO implements Serializable {
    private Long id;
    private String title;
    private Long influencerId;
    private String influencerCode;
    private String cover;
    private String displayName;
    private String description;
    private Integer goodsSortType;
}
