package com.love.user.sdk.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
    private Integer status;
    private String notes;
    private String uid;
    private Integer source;
    private LocalDateTime createTime;
    private List<UserAddressDTO> addressList;

    public String getFullName(){
        return String.format("%s %s",firstName, lastName);
    }
}
