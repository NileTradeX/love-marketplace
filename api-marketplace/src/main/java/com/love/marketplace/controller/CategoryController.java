package com.love.marketplace.controller;

import com.love.common.result.Result;
import com.love.marketplace.manager.CategoryManager;
import com.love.marketplace.model.param.CategoryQueryByPidParam;
import com.love.marketplace.model.param.CategoryQueryTreeParam;
import com.love.marketplace.model.vo.CategoryTreeVO;
import com.love.marketplace.model.vo.CategoryVO;
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

    @GetMapping("queryByPid")
    public Result<List<CategoryVO>> queryByPid(CategoryQueryByPidParam categoryQueryByPidParam) {
        return Result.success(categoryManager.queryByPid(categoryQueryByPidParam));
    }

    @GetMapping("tree")
    public Result<List<CategoryTreeVO>> tree(CategoryQueryTreeParam categoryQueryTreeParam) {
        return Result.success(categoryManager.tree(categoryQueryTreeParam));
    }
}
