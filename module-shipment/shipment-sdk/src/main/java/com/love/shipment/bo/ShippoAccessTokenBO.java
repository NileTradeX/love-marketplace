package com.love.shipment.bo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ShippoAccessTokenBO implements Serializable {
    private Long merchantId;
    private String accessToken;
}
