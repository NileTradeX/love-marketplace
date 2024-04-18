package com.love.goods.bo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CategoryQueryPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private Integer level;
    private Integer type;
}
