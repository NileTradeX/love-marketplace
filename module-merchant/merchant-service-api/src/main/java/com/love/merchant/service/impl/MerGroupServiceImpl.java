package com.love.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.merchant.entity.MerGroup;
import com.love.merchant.mapper.MerGroupMapper;
import com.love.merchant.service.MerGroupService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MerGroupServiceImpl extends ServiceImpl<MerGroupMapper, MerGroup> implements MerGroupService {
    @Override
    public boolean save(MerGroup merGroup) {
        return super.save(merGroup);
    }

    @Override
    public boolean checkExists(String bizName) {
        return Objects.nonNull(this.lambdaQuery().eq(MerGroup::getName, bizName).one());
    }

    @Override
    public MerGroup queryByName(String bizName) {
        return this.lambdaQuery().eq(MerGroup::getName, bizName).one();
    }

    @Override
    public MerGroup queryById(Long groupId) {
        return this.getById(groupId);
    }
}
