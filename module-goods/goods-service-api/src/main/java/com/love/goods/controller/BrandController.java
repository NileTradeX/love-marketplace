package com.love.goods.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.common.result.Result;
import com.love.goods.bo.*;
import com.love.goods.dto.BrandDTO;
import com.love.goods.enums.BrandStatus;
import com.love.goods.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BrandController {

    private final BrandService brandService;

    @PostMapping("save")
    public Result<Long> save(@RequestBody BrandSaveBO brandSaveBO) {
        return Result.success(brandService.save(brandSaveBO));
    }

    @PostMapping("saveBatch")
    public Result<Boolean> saveBatch(@RequestBody BrandBatchSaveBO brandBatchSaveBO) {
        return Result.success(brandService.saveBatch(brandBatchSaveBO.getBrands()));
    }

    @GetMapping("queryById")
    public Result<BrandDTO> detail(IdParam idParam) {
        return Result.success(brandService.queryById(idParam));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(brandService.deleteById(idParam));
    }

    @GetMapping("queryByMerchantId")
    public Result<List<BrandDTO>> queryByMerchantId(BrandQueryListBO brandQueryListBO) {
        return Result.success(brandService.queryByMerchantId(brandQueryListBO));
    }

    @GetMapping("page")
    public Result<Pageable<BrandDTO>> page(BrandQueryPageBO brandQueryPageBO) {
        return Result.success(brandService.page(brandQueryPageBO));
    }

    @GetMapping("disable")
    public Result<Boolean> disable(BrandUpdateStatusBO brandUpdateStatusBO) {
        return Result.success(brandService.updateStatus(brandUpdateStatusBO.getMerchantId(), BrandStatus.DISABLE));
    }

    @GetMapping("enable")
    public Result<Boolean> enabled(BrandUpdateStatusBO brandUpdateStatusBO) {
        return Result.success(brandService.updateStatus(brandUpdateStatusBO.getMerchantId(), BrandStatus.ENABLE));
    }

    @GetMapping("queryByIds")
    public Result<List<BrandDTO>> listByIds(IdsParam idsParam) {
        return Result.success(brandService.queryByIds(idsParam));
    }
}
