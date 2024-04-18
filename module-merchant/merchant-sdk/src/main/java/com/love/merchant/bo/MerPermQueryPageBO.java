package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerPermQueryPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private String title;
    private Integer pid;

}
