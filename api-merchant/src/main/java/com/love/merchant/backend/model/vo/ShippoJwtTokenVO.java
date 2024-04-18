package com.love.merchant.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShippoJwtTokenVO implements Serializable {

    private String token;

    private Long expiresIn;
}
