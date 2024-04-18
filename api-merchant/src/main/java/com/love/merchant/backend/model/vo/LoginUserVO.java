package com.love.merchant.backend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUserVO implements Serializable {
    private String token;
    private MerUserVO user;
    private List<MerPermVO> perms;
}
