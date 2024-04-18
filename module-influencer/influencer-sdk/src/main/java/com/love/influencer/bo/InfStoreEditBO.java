package com.love.influencer.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfStoreEditBO implements Serializable {
    private Long id;
    private String title;
    private Long influencerId;
    private String cover;
    private String displayName;
    private String description;
    private Integer goodsSortType;
}
