package com.love.merchant.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginUserDTO implements Serializable {
    private MerUserDTO user;
    private List<MerPermDTO> perms;

    public LoginUserDTO(MerUserDTO user, List<MerPermDTO> perms) {
        this.user = user;
        this.perms = perms;
    }
}
