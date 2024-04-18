package com.love.order.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderRefundAbnormalBO implements Serializable {
    private Long refundId;
    private String comment;
}
