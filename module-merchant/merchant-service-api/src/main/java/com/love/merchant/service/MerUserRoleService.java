package com.love.merchant.service;

import com.love.common.param.IdParam;
import com.love.merchant.entity.MerUserRole;

public interface MerUserRoleService {

    boolean save(MerUserRole merUserRole);

    MerUserRole queryByUserId(long userId);

    boolean deleteByUserId(long userId);

    Long countByRoleId(IdParam idParam);
}
