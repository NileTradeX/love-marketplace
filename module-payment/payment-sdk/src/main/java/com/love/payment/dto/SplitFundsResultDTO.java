package com.love.payment.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SplitFundsResultDTO implements Serializable {
    private boolean success = true;
    private String msg;
    private String data;
}
