package com.love.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderSimpleStatVO implements Serializable {
    private long pendingShipment;
    private long pendingReceipt;
}
