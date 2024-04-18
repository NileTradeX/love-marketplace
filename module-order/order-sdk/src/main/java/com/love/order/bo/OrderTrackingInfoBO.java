package com.love.order.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 暂时方案，先把物流信息存到订单表
 */
@Data
public class OrderTrackingInfoBO implements Serializable {
    private Long merchantOrderId;
    private String carriers;
    private String trackingNo;
    private String shippoTransId;
}
