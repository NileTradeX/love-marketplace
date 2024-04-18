package com.love.backend.controller;

import com.love.backend.manager.CategoryManager;
import com.love.backend.model.param.CategoryEditParam;
import com.love.backend.model.param.CategoryQueryPageParam;
import com.love.backend.model.param.CategoryQueryTreeParam;
import com.love.backend.model.param.CategorySaveParam;
import com.love.backend.model.vo.CategoryTreeVO;
import com.love.backend.model.vo.CategoryVO;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods/category")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "GoodsCategoryApi", description = "All Goods category operation")
public class CategoryController {

    private final CategoryManager categoryManager;

    @PostMapping("save")
    @Operation(operationId = "saveCategory")
    public Result<Long> save(@RequestBody @Validated CategorySaveParam categorySaveParam) {
        return Result.success(categoryManager.save(categorySaveParam));
    }


    @PostMapping("edit")
    @Operation(operationId = "editCategory")
    public Result<CategoryVO> edit(@RequestBody @Validated CategoryEditParam categoryEditParam) {
        return Result.success(categoryManager.edit(categoryEditParam));
    }


    @GetMapping("deleteById")
    @Operation(operationId = "deleteCategory")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(categoryManager.deleteById(idParam));
    }

    @GetMapping("page")
    @Operation(operationId = "queryCategoryPage")
    public Result<Pageable<CategoryVO>> page(CategoryQueryPageParam categoryQueryPageParam) {
        return Result.success(categoryManager.page(categoryQueryPageParam));
    }

    @GetMapping("tree")
    @Operation(operationId = "queryCategoryTree")
    public Result<List<CategoryTreeVO>> tree(CategoryQueryTreeParam categoryQueryTreeParam) {
        return Result.success(categoryManager.tree(categoryQueryTreeParam));
    }
}
