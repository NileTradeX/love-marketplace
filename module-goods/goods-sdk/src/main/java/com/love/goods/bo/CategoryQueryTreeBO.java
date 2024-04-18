package com.love.goods.bo;

import lombok.Data;
import lombok.Builder;

import java.io.Serializable;

@Data
@Builder
public class CategoryQueryTreeBO implements Serializable {
    private Integer level = 1;
    private boolean attachGoodsType = true;
    private Integer type;
}
