package com.love.merchant.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.merchant.bo.MerRoleEditBO;
import com.love.merchant.bo.MerRoleQueryPageBO;
import com.love.merchant.bo.MerRoleSaveBO;
import com.love.merchant.bo.SettingPermsBO;
import com.love.merchant.dto.MerPermDTO;
import com.love.merchant.dto.MerRoleDTO;

import java.util.List;


public interface MerRoleService {

    MerRoleDTO save(MerRoleSaveBO merRoleSaveBO);

    MerRoleDTO edit(MerRoleEditBO merRoleEditBO);

    MerRoleDTO queryById(IdParam idParam);

    Boolean deleteById(IdParam idParam);

    Pageable<MerRoleDTO> page(MerRoleQueryPageBO merRoleQueryPageBO);

    List<MerPermDTO> queryPerms(Long roleId);

    Boolean settingPerms(SettingPermsBO settingPermsBO);
}
