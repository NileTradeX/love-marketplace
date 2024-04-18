package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GoodsHomepageQueryBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private List<Long> firstCateIds;
    private List<Long> secondCateIds;
    private List<Long> brandIds;
    private List<GoodsPriceRangeBO> priceRanges;
}
