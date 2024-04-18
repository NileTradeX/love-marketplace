package com.love.shipment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippoOauthTokenDTO implements Serializable {
    private Long id;
    private Long merchantId;
    private String accessToken;
    private LocalDateTime createTime;
}
