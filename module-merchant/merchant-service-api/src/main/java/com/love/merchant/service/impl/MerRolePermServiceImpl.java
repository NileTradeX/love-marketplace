package com.love.merchant.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.param.IdParam;
import com.love.merchant.bo.SettingPermsBO;
import com.love.merchant.entity.MerRolePerm;
import com.love.merchant.mapper.MerRolePermMapper;
import com.love.merchant.service.MerRolePermService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MerRolePermServiceImpl extends ServiceImpl<MerRolePermMapper, MerRolePerm> implements MerRolePermService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveBatch(SettingPermsBO settingPermsBO) {
        this.lambdaUpdate().eq(MerRolePerm::getRoleId, settingPermsBO.getRoleId()).remove();

        List<Long> permIds = settingPermsBO.getPermIds();
        List<MerRolePerm> rolePermList = permIds.stream()
                .map(permId -> {
                    MerRolePerm sysRolePerm = new MerRolePerm();
                    sysRolePerm.setPermId(permId);
                    sysRolePerm.setRoleId(settingPermsBO.getRoleId());
                    return sysRolePerm;
                })
                .collect(Collectors.toList());

        return this.saveBatch(rolePermList);
    }


    @Override
    public Map<Long, MerRolePerm> getRolePermMap(Long roleId) {
        List<MerRolePerm> merRolePerms = new ArrayList<>();
        if (roleId != null) {
            merRolePerms = this.lambdaQuery().eq(MerRolePerm::getRoleId, roleId).list();
        }

        if (CollectionUtil.isNotEmpty(merRolePerms)) {
            return merRolePerms.stream().collect(Collectors.toMap(MerRolePerm::getPermId, Function.identity()));
        }
        return Collections.emptyMap();
    }

    @Override
    public Long countByRoleId(IdParam idParam) {
        QueryWrapper<MerRolePerm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", idParam.getId());
        return this.count(queryWrapper);
    }
}

