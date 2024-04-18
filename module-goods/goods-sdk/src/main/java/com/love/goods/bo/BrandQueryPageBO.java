package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandQueryPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private Integer status;
}
