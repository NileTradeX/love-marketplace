package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserQueryPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private String account;
    private String username;
    private Long groupId;
}
