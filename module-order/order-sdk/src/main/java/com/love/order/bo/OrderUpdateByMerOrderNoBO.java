package com.love.order.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderUpdateByMerOrderNoBO implements Serializable {
    private String merOrderNo;
    private Integer status;
    private String reason;
    private List<Long> skuIdList;
    private boolean cascade;
}
