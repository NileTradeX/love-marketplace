package com.love.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.param.IdParam;
import com.love.rbac.entity.SysUserRole;
import com.love.rbac.mapper.SysUserRoleMapper;
import com.love.rbac.service.SysUserRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Override
    public boolean save(SysUserRole entity) {
        return super.save(entity);
    }

    @Override
    public SysUserRole queryByUserId(long userId) {
        return this.lambdaQuery().eq(SysUserRole::getUserId, userId).one();
    }

    @Override
    public boolean deleteByUserId(long userId) {
        return this.lambdaUpdate().eq(SysUserRole::getUserId, userId).remove();
    }

    @Override
    public Long countByRoleId(IdParam idParam) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", idParam.getId());
        return this.count(queryWrapper);
    }

}
