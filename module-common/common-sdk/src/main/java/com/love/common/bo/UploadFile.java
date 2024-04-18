package com.love.common.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadFile implements Serializable {
    private Long userId;
    private String oriName;
    private long size;
    private String contentType;
    private byte[] data;
}
