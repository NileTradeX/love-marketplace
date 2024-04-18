package com.love.user.sdk.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddressDTO implements Serializable {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String city;
    private String state;
    private String zipCode;
    private String company;
    private String address;
    private Integer isDefault;

}
