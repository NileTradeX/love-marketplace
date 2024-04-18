package com.love.merchant.backend.controller;

import com.love.common.page.Pageable;
import com.love.common.result.Result;
import com.love.merchant.backend.manager.CategoryManager;
import com.love.merchant.backend.model.param.CategoryQueryPageParam;
import com.love.merchant.backend.model.param.CategoryQueryTreeParam;
import com.love.merchant.backend.model.vo.CategoryTreeVO;
import com.love.merchant.backend.model.vo.CategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("goods/category")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "GoodsCategoryApi", description = "All Goods category operation")
public class CategoryController {

    private final CategoryManager categoryManager;

    @GetMapping("page")
    @Operation(operationId = "queryCategoryPage")
    public Result<Pageable<CategoryVO>> page(CategoryQueryPageParam categoryQueryPageParam) {
        return Result.success(categoryManager.page(categoryQueryPageParam));
    }

    @GetMapping("tree")
    @Operation(operationId = "categoryTree")
    public Result<List<CategoryTreeVO>> tree(CategoryQueryTreeParam categoryQueryTreeParam) {
        return Result.success(categoryManager.tree(categoryQueryTreeParam));
    }
}
