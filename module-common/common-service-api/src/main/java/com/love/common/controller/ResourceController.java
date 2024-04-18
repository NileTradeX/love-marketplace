package com.love.common.controller;

import com.love.common.bo.ResourceQueryPageBO;
import com.love.common.dto.ResourceDTO;
import com.love.common.page.Pageable;
import com.love.common.result.Result;
import com.love.common.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("resource")
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping("page")
    public Result<Pageable<ResourceDTO>> page(ResourceQueryPageBO resourceQueryPageBO) {
        return Result.success(resourceService.page(resourceQueryPageBO));
    }
}
