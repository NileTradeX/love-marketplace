package com.love.merchant.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerchantRegInfo implements Serializable {
    private Long merchantId;
    private Long groupId;
    private String bizName;
    private String code;
    private Integer status;
    private String email;
}
