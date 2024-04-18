package com.love.user.sdk.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserQueryPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private String customerName;
    private String sort;
}
