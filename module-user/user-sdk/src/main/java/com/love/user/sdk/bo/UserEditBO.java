package com.love.user.sdk.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserEditBO implements Serializable {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
    private String notes;
    private String password;
    private List<UserAddressEditBO> addressList;

}
