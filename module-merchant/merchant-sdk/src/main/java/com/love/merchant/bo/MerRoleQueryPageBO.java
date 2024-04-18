package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerRoleQueryPageBO implements Serializable {
    private int pageNum;
    private int pageSize;
    private Long groupId;
}
