package com.love.order.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderRefundUserCreateBO implements Serializable {
    private Long orderId;
    private String reason;
    private Integer type;
    private Long userId;
    private List<Long> userIdList;
}
