package com.love.shipment.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippoTrackBO implements Serializable {
    private String carriers;
    private String trackingNo;
    private String trackingInfo;
    private Long merchantOrderId;
}
