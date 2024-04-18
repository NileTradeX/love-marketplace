package com.love.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfUserVO implements Serializable {
    private Long id;
    private String account;
    private String firstName;
    private String lastName;
    private String code;
    private Integer status;
}
