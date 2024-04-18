package com.love.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceDTO implements Serializable {
    private Long id;
    private Long userId;
    private String oriName;
    private Integer size;
    private String key;
    private String ext;
    private Integer type;
    private Integer width;
    private Integer height;
}
