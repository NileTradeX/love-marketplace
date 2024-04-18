package com.love.influencer.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfUserAddressDTO implements Serializable {
    private Long id;
    private Long userId;
    private String country;
    private String phoneNumber;
    private String city;
    private String state;
    private String zipCode;
    private String address;
    private Integer isDefault;
}
