package com.love.influencer.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfStoreIdDTO implements Serializable {
    private Long id;
    private Integer goodsSortType;
}
