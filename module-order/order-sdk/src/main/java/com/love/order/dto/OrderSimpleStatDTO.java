package com.love.order.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderSimpleStatDTO implements Serializable {
    private long pendingShipment;
    private long pendingReceipt;
}
