package com.love.merchant.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShippoOauthTokenVO implements Serializable {
    private String accessToken;
}
