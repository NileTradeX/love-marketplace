package com.love.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.param.IdParam;
import com.love.merchant.entity.MerUserRole;
import com.love.merchant.mapper.MerUserRoleMapper;
import com.love.merchant.service.MerUserRoleService;
import org.springframework.stereotype.Service;

@Service
public class MerUserRoleServiceImpl extends ServiceImpl<MerUserRoleMapper, MerUserRole> implements MerUserRoleService {
    @Override
    public boolean save(MerUserRole merUserRole) {
        return super.save(merUserRole);
    }

    @Override
    public MerUserRole queryByUserId(long userId) {
        return this.lambdaQuery().eq(MerUserRole::getUserId, userId).one();
    }

    @Override
    public boolean deleteByUserId(long userId) {
        return this.lambdaUpdate().eq(MerUserRole::getUserId, userId).remove();
    }

    @Override
    public Long countByRoleId(IdParam idParam) {
        QueryWrapper<MerUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", idParam.getId());
        return this.count(queryWrapper);
    }

}
