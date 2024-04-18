package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GoodsPriceRangeBO implements Serializable {

    private BigDecimal min;
    private BigDecimal max;

    public BigDecimal getMin() {
        return min == null ? BigDecimal.ZERO : min;
    }

    public BigDecimal getMax() {
        return max == null ? BigDecimal.valueOf(Integer.MAX_VALUE) : max;
    }

}
