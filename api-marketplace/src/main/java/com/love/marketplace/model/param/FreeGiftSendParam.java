package com.love.marketplace.model.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class FreeGiftSendParam implements Serializable {
    private String userIdList;
}
