package com.love.merchant.service;

import com.love.merchant.bo.MerUserAdminPersonalInfoBO;
import com.love.merchant.entity.MerUserAdminPersonalInfo;

public interface MerUserAdminPersonalInfoService {

    boolean save(MerUserAdminPersonalInfoBO personalInfo);

    MerUserAdminPersonalInfo queryById(Long id);

    boolean updateById(MerUserAdminPersonalInfoBO personalInfo);
}
