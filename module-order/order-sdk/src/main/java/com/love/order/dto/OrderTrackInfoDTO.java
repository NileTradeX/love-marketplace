package com.love.order.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderTrackInfoDTO implements Serializable {
    private Long id;
    private String carriers;
    private String trackingNo;
}
