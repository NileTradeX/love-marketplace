package com.love.rbac.service;

import com.love.common.param.IdParam;
import com.love.rbac.bo.SettingPermsBO;
import com.love.rbac.entity.SysRolePerm;

import java.util.Map;


public interface SysRolePermService {


    Boolean saveBatch(SettingPermsBO settingPermsBO);

    Map<Long, SysRolePerm> getRolePermMap(Long roleId);

    Long countByRoleId(IdParam idParam);
}