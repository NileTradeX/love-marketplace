package com.love.merchant.service;

import com.love.merchant.entity.MerGroup;

public interface MerGroupService {
    boolean save(MerGroup merGroup);

    boolean checkExists(String bizName);

    MerGroup queryByName(String bizName);

    MerGroup queryById(Long groupId);
}
