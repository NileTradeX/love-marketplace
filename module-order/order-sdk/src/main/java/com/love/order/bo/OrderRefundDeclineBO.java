package com.love.order.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderRefundDeclineBO implements Serializable {
    private Long refundId;
    private String comment;
}
