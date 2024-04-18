package com.love.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LabelVO implements Serializable {
    private Long id;
    private String name;
}
