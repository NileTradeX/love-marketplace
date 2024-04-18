package com.love.rbac.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.rbac.bo.SettingPermsBO;
import com.love.rbac.bo.SysRoleEditBO;
import com.love.rbac.bo.SysRoleQueryPageBO;
import com.love.rbac.bo.SysRoleSaveBO;
import com.love.rbac.dto.SysPermDTO;
import com.love.rbac.dto.SysRoleDTO;

import java.util.List;


public interface SysRoleService {

    SysRoleDTO save(SysRoleSaveBO sysRoleSaveBO);

    SysRoleDTO edit(SysRoleEditBO sysRoleEditBO);

    SysRoleDTO queryById(IdParam idParam);

    Boolean deleteById(IdParam idParam);

    Pageable<SysRoleDTO> page(SysRoleQueryPageBO sysRoleQueryPageBO);

    List<SysPermDTO> queryPerms(Long roleId);

    Boolean settingPerms(SettingPermsBO settingPermsBO);
}
