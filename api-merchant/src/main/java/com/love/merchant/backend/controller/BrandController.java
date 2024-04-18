package com.love.merchant.backend.controller;

import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.merchant.backend.manager.BrandManager;
import com.love.merchant.backend.model.param.BrandQueryListParam;
import com.love.merchant.backend.model.param.BrandSaveParam;
import com.love.merchant.backend.model.vo.BrandVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods/brand")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "BrandApi", description = "All Goods category operation")
public class BrandController {

    private final BrandManager brandManager;

    @PostMapping("save")
    @Operation(operationId = "saveBrand")
    public Result<Long> save(@RequestBody @Validated BrandSaveParam brandSaveParam) {
        return Result.success(brandManager.save(brandSaveParam));
    }

    @GetMapping("list")
    @Operation(operationId = "queryBrandList")
    public Result<List<BrandVO>> list(@Validated BrandQueryListParam brandQueryListParam) {
        return Result.success(brandManager.list(brandQueryListParam));
    }

    @GetMapping("deleteById")
    @Operation(operationId = "deleteBrand")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(brandManager.deleteById(idParam));
    }
}
