package com.love.backend.controller;

import com.love.backend.manager.PermManager;
import com.love.backend.model.param.*;
import com.love.backend.model.vo.PermVO;
import com.love.common.page.Pageable;
import com.love.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sys/perm")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "SysPermApi", description = "all SysPerm manage operation")
public class PermController {

    private final PermManager permManager;

    @PostMapping("save")
    @Operation(operationId = "savePerm")
    public Result<PermVO> save(@RequestBody @Validated PermSaveParam permSaveParam) {
        return Result.success(permManager.save(permSaveParam));
    }

    @PostMapping("edit")
    @Operation(operationId = "editPerm")
    public Result<PermVO> edit(@RequestBody @Validated PermEditParam permEditParam) {
        return Result.success(permManager.edit(permEditParam));
    }

    @GetMapping("deleteById")
    @Operation(operationId = "deletePerm")
    public Result<Boolean> deleteById(PermDeleteParam permDeleteParam) {
        return Result.success(permManager.deleteById(permDeleteParam));
    }

    @GetMapping("page")
    @Operation(operationId = "queryPermPage")
    public Result<Pageable<PermVO>> page(PermQueryPageParam permQueryPageParam) {
        return Result.success(permManager.page(permQueryPageParam));
    }

    @GetMapping("tree")
    @Operation(operationId = "queryPermTree")
    public Result<List<PermVO>> tree(PermTreeQueryParam permTreeQueryParam) {
        return Result.success(permManager.tree(permTreeQueryParam));
    }
}
