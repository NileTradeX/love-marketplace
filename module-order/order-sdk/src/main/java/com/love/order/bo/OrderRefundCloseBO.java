package com.love.order.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderRefundCloseBO implements Serializable {
    private Long refundId;
}
