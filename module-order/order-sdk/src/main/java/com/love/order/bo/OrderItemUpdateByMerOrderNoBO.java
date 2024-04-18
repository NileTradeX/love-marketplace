package com.love.order.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderItemUpdateByMerOrderNoBO implements Serializable {
    private String merOrderNo;
    private Integer status;
    private List<Long> skuIdList;
}
