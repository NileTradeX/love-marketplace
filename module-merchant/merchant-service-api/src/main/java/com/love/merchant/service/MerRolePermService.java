package com.love.merchant.service;

import com.love.common.param.IdParam;
import com.love.merchant.bo.SettingPermsBO;
import com.love.merchant.entity.MerRolePerm;

import java.util.Map;


public interface MerRolePermService {

    Boolean saveBatch(SettingPermsBO settingPermsBO);

    Map<Long, MerRolePerm> getRolePermMap(Long roleId);

    Long countByRoleId(IdParam idParam);
}