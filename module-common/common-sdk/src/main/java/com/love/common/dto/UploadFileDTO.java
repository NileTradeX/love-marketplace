package com.love.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadFileDTO implements Serializable {
    private Long userId;
    private String oriName;
    private String key;
    private String ext;
}
