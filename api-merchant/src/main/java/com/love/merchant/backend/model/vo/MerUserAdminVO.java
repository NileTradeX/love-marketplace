package com.love.merchant.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserAdminVO implements Serializable {
    private Long id;
    private String account;
}
