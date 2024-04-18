package com.love.rbac.service;

import com.love.common.param.IdParam;
import com.love.rbac.entity.SysUserRole;

public interface SysUserRoleService {

    boolean save(SysUserRole sysUserRole);

    SysUserRole queryByUserId(long userId);

    boolean deleteByUserId(long userId);

    Long countByRoleId(IdParam idParam);
}
