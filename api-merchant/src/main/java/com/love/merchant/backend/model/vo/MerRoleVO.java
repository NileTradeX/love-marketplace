package com.love.merchant.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerRoleVO implements Serializable {
    private Long id;
    private String name;
    private Long groupId;
}
