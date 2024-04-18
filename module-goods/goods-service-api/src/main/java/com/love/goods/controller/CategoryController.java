package com.love.goods.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.goods.bo.*;
import com.love.goods.dto.CategoryDTO;
import com.love.goods.dto.CategoryTreeDTO;
import com.love.goods.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("save")
    public Result<Long> save(@RequestBody CategorySaveBO categorySaveBO) {
        return Result.success(categoryService.save(categorySaveBO));
    }

    @PostMapping("edit")
    public Result<CategoryDTO> edit(@RequestBody CategoryEditBO categoryEditBO) {
        return Result.success(categoryService.edit(categoryEditBO));
    }

    @GetMapping("queryById")
    public Result<CategoryDTO> queryById(IdParam idParam) {
        return Result.success(categoryService.queryById(idParam));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(categoryService.deleteById(idParam));
    }

    @GetMapping("page")
    public Result<Pageable<CategoryDTO>> page(CategoryQueryPageBO categoryQueryPageBO) {
        return Result.success(categoryService.page(categoryQueryPageBO));
    }

    @GetMapping("tree")
    public Result<List<CategoryTreeDTO>> tree(CategoryQueryTreeBO categoryQueryTreeBO) {
        return Result.success(categoryService.tree(categoryQueryTreeBO));
    }

    @GetMapping("queryByPid")
    public Result<List<CategoryDTO>> queryByPid(CategoryQueryByPidBO categoryQueryByPidBO) {
        return Result.success(categoryService.queryByPid(categoryQueryByPidBO));
    }
}
