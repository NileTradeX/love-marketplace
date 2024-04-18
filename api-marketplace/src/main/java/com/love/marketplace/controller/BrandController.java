package com.love.marketplace.controller;

import com.love.common.page.Pageable;
import com.love.common.param.PageParam;
import com.love.common.result.Result;
import com.love.marketplace.manager.BrandManager;
import com.love.marketplace.model.vo.BrandVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("goods/brand")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "BrandApi", description = "All Goods brand operation")
public class BrandController {

    private final BrandManager brandManager;

    @GetMapping("page")
    @Operation(operationId = "queryBrandPage")
    public Result<Pageable<BrandVO>> page(PageParam param) {
        return Result.success(brandManager.page(param));
    }
}
