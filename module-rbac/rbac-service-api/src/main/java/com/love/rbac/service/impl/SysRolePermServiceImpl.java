package com.love.rbac.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.param.IdParam;
import com.love.rbac.bo.SettingPermsBO;
import com.love.rbac.entity.SysRolePerm;
import com.love.rbac.mapper.SysRolePermMapper;
import com.love.rbac.service.SysRolePermService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SysRolePermServiceImpl extends ServiceImpl<SysRolePermMapper, SysRolePerm> implements SysRolePermService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveBatch(SettingPermsBO settingPermsBO) {
        this.lambdaUpdate().eq(SysRolePerm::getRoleId, settingPermsBO.getRoleId()).remove();

        List<Long> permIds = settingPermsBO.getPermIds();
        List<SysRolePerm> rolePermList = permIds.stream()
                .map(permId -> {
                    SysRolePerm sysRolePerm = new SysRolePerm();
                    sysRolePerm.setPermId(permId);
                    sysRolePerm.setRoleId(settingPermsBO.getRoleId());
                    return sysRolePerm;
                })
                .collect(Collectors.toList());

        return this.saveBatch(rolePermList);
    }


    @Override
    public Map<Long, SysRolePerm> getRolePermMap(Long roleId) {
        List<SysRolePerm> sysRolePerms = new ArrayList<>();
        if (roleId != null) {
            sysRolePerms = this.lambdaQuery().eq(SysRolePerm::getRoleId, roleId).list();
        }

        if (CollectionUtil.isNotEmpty(sysRolePerms)) {
            return sysRolePerms.stream().collect(Collectors.toMap(SysRolePerm::getPermId, Function.identity()));
        }
        return Collections.emptyMap();
    }

    @Override
    public Long countByRoleId(IdParam idParam) {
        QueryWrapper<SysRolePerm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", idParam.getId());
        return this.count(queryWrapper);
    }
}

