package com.love.shipment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippoJwtTokenDTO implements Serializable {
    private String token;
    private Long expiresIn;
}
