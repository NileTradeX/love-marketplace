package com.love.user.sdk.bo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class UserSaveBO implements Serializable {
    private String avatar;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Integer source;
    private String notes;
    private LocalDateTime lastLoginTime;
    private Integer acceptTermsOfService;
    private Integer subscribeToNewsletter;
    private List<UserAddressSaveBO> addressList;
    private Integer status;
}

