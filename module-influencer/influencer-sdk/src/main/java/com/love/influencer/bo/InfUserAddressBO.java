package com.love.influencer.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfUserAddressBO implements Serializable {
    private Long id;
    private Long influencerId;
    private String country;
    private String phoneNumber;
    private String city;
    private String state;
    private String zipCode;
    private String address;
    private Integer isDefault;
}
