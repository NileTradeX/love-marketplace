package com.love.user.sdk.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddressSaveBO implements Serializable {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String city;
    private String state;
    private String zipCode;
    private String address;
    private Integer isDefault;
    private String company;
}
