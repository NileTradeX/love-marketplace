package com.love.common.service;

import com.love.common.bo.ResourceQueryPageBO;
import com.love.common.dto.ResourceDTO;
import com.love.common.entity.Resource;
import com.love.common.page.Pageable;

public interface ResourceService {

    boolean save(Resource resource);

    Pageable<ResourceDTO> page(ResourceQueryPageBO resourceQueryPageBO);
}
