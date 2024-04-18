package com.love.merchant.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.merchant.bo.MerUserAdminPersonalInfoBO;
import com.love.merchant.entity.MerUserAdminPersonalInfo;
import com.love.merchant.mapper.MerUserAdminPersonalInfoMapper;
import com.love.merchant.service.MerUserAdminPersonalInfoService;
import org.springframework.stereotype.Service;

@Service
public class MerUserAdminPersonalInfoServiceImpl extends ServiceImpl<MerUserAdminPersonalInfoMapper, MerUserAdminPersonalInfo> implements MerUserAdminPersonalInfoService {

    @Override
    public boolean save(MerUserAdminPersonalInfoBO personalInfo) {
        MerUserAdminPersonalInfo merUserAdminPersonalInfo = BeanUtil.copyProperties(personalInfo, MerUserAdminPersonalInfo.class);
        return this.save(merUserAdminPersonalInfo);
    }

    @Override
    public MerUserAdminPersonalInfo queryById(Long id) {
        return this.getById(id);
    }

    @Override
    public boolean updateById(MerUserAdminPersonalInfoBO personalInfo) {
        return this.updateById(BeanUtil.copyProperties(personalInfo, MerUserAdminPersonalInfo.class));
    }
}
