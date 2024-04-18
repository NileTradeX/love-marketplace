package com.love.influencer.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfStoreChangeCoverBO implements Serializable {
    private Long influencerId;
    private String cover;
}
