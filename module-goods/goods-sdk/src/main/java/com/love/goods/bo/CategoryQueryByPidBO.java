package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryQueryByPidBO implements Serializable {
    private Integer pid = 0;
}
