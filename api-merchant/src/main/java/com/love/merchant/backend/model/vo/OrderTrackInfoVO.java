package com.love.merchant.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderTrackInfoVO implements Serializable {
    private Long id;
    private String carriers;
    private String trackingNo;
    private String shippoTransId;
}
