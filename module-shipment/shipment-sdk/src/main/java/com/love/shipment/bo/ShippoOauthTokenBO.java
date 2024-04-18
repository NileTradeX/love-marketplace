package com.love.shipment.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShippoOauthTokenBO implements Serializable {
    private Long merchantId;
    private String code;
}
