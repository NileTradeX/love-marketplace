package com.love.influencer.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InfUserSaveBO implements Serializable {
    private String account;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private BigDecimal commissionRate;
}

