package com.love.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.bo.ResourceQueryPageBO;
import com.love.common.dto.ResourceDTO;
import com.love.common.entity.Resource;
import com.love.common.mapper.ResourceMapper;
import com.love.common.page.Pageable;
import com.love.common.service.ResourceService;
import com.love.common.util.PageUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Override
    public boolean save(Resource entity) {
        return super.save(entity);
    }

    @Override
    public Pageable<ResourceDTO> page(ResourceQueryPageBO resourceQueryPageBO) {
        Page<Resource> page = this.lambdaQuery()
                .eq(Objects.nonNull(resourceQueryPageBO.getType()), Resource::getType, resourceQueryPageBO.getType())
                .eq(Objects.nonNull(resourceQueryPageBO.getName()), Resource::getOriName, resourceQueryPageBO.getName())
                .page(new Page<>(resourceQueryPageBO.getPageNum(), resourceQueryPageBO.getPageSize()));
        return PageUtil.toPage(page, ResourceDTO.class);
    }

}
