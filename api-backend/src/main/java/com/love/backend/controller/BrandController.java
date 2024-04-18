package com.love.backend.controller;

import com.love.backend.manager.BrandManager;
import com.love.backend.model.param.BrandQueryListParam;
import com.love.backend.model.vo.BrandVO;
import com.love.common.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("goods/brand")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "BrandAPi", description = "All Goods category operation")
public class BrandController {

    private final BrandManager brandManager;

    @GetMapping("list")
    public Result<List<BrandVO>> list(@Validated BrandQueryListParam brandQueryListParam) {
        return Result.success(brandManager.list(brandQueryListParam));
    }
}
