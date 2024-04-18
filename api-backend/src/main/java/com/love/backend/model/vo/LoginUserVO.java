package com.love.backend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVO implements Serializable {
    private String token;
    private SysUserVO user;
    private List<PermVO> perms;
}
