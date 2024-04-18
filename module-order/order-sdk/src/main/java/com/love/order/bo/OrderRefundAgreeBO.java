package com.love.order.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderRefundAgreeBO implements Serializable {
    private String orderNo;
    private Long refundId;
}
