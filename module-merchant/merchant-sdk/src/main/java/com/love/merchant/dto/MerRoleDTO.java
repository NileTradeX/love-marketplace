package com.love.merchant.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class MerRoleDTO implements Serializable {
    private Long id;
    private String name;
    private Long groupId;
}
