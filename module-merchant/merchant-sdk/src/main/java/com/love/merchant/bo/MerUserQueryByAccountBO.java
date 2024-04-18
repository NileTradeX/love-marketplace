package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserQueryByAccountBO implements Serializable {
    private String account;
}
