package com.love.influencer.bo;

import lombok.Data;

import java.io.Serializable;


@Data
public class InfUserEditBO implements Serializable {
    private Long id;
    private String generalIntroduction;
    private String socialLinks;
    private String paypalAccount;
    private String password;
    private InfUserAddressBO userAddress;
    private String code;
}
