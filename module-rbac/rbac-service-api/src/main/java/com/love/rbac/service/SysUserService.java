package com.love.rbac.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.rbac.bo.*;
import com.love.rbac.dto.LoginUserDTO;
import com.love.rbac.dto.SysUserDTO;

public interface SysUserService {

    SysUserDTO save(SysUserSaveBO sysUserSaveBO);

    SysUserDTO edit(SysUserEditBO sysUserEditBO);

    SysUserDTO queryById(IdParam idParam);

    Boolean deleteById(IdParam idParam);

    Pageable<SysUserDTO> page(SysUserQueryPageBO sysUserQueryPageBO);

    Boolean changePassword(SysUserChangePasswordBO sysUserChangePasswordBO);

    LoginUserDTO login(SysUserLoginBO sysUserLoginBO);

    Boolean isSuper(Long userId);
}
