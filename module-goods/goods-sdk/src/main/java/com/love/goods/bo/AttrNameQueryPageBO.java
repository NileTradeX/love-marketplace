package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrNameQueryPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
}
