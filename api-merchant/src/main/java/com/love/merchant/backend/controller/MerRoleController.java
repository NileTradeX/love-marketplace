package com.love.merchant.backend.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.PageParam;
import com.love.common.result.Result;
import com.love.merchant.backend.manager.MerRoleManager;
import com.love.merchant.backend.model.param.MerRoleEditParam;
import com.love.merchant.backend.model.param.MerRoleSaveParam;
import com.love.merchant.backend.model.param.SettingPermsParam;
import com.love.merchant.backend.model.vo.MerPermVO;
import com.love.merchant.backend.model.vo.MerRoleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("mer/role")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "MerRoleApi", description = "all MerRole manage operation")
public class MerRoleController {

    private final MerRoleManager merRoleManager;

    @PostMapping("save")
    @Operation(operationId = "saveMerRole")
    public Result<MerRoleVO> save(@RequestBody @Validated MerRoleSaveParam merRoleSaveParam) {
        return Result.success(merRoleManager.save(merRoleSaveParam));
    }


    @PostMapping("edit")
    @Operation(operationId = "editMerRole")
    public Result<MerRoleVO> edit(@RequestBody @Validated MerRoleEditParam merRoleEditParam) {
        return Result.success(merRoleManager.edit(merRoleEditParam));
    }


    @GetMapping("detail")
    @Operation(operationId = "merRoleDetail")
    public Result<MerRoleVO> detail(IdParam idParam) {
        return Result.success(merRoleManager.detail(idParam));
    }


    @GetMapping("deleteById")
    @Operation(operationId = "deleteMerRole")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(merRoleManager.deleteById(idParam));
    }


    @GetMapping("page")
    @Operation(operationId = "queryMerRolePage")
    public Result<Pageable<MerRoleVO>> list(PageParam pageParam) {
        return Result.success(merRoleManager.page(pageParam));
    }


    @GetMapping("queryPerms")
    @Operation(operationId = "queryPerms")
    public Result<List<MerPermVO>> queryPerms(IdParam idParam) {
        return Result.success(merRoleManager.queryPerms(idParam));
    }


    @PostMapping("settingPerms")
    @Operation(operationId = "settingPerms")
    public Result<Boolean> settingPerms(@RequestBody @Validated SettingPermsParam settingPermsParam) {
        return Result.success(merRoleManager.settingPerms(settingPermsParam));
    }

}
