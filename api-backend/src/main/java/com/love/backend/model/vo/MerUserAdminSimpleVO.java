package com.love.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserAdminSimpleVO implements Serializable {
    private Long id;
    private String account;
    private String username;
    private Integer status;
    private String bizName;
}
