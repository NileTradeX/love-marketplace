package com.love.order.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderRefundUserCloseBO implements Serializable {
    private Long refundId;
    private Long userId;
    private List<Long> userIdList;
}
