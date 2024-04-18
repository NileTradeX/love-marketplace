package com.love.backend.controller;

import com.love.backend.manager.SysRoleManager;
import com.love.backend.model.param.SettingPermsParam;
import com.love.backend.model.param.SysRoleEditParam;
import com.love.backend.model.param.SysRoleSaveParam;
import com.love.backend.model.vo.PermVO;
import com.love.backend.model.vo.SysRoleVO;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.PageParam;
import com.love.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("sys/role")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "SysRoleApi", description = "all SysRole manage operation")
public class SysRoleController {

    private final SysRoleManager sysRoleManager;

    @PostMapping("save")
    @Operation(operationId = "saveSysRole")
    public Result<SysRoleVO> save(@RequestBody @Validated SysRoleSaveParam sysRoleSaveParam) {
        return Result.success(sysRoleManager.save(sysRoleSaveParam));
    }

    @PostMapping("edit")
    @Operation(operationId = "editSysRole")
    public Result<SysRoleVO> edit(@RequestBody @Validated SysRoleEditParam sysRoleEditParam) {
        return Result.success(sysRoleManager.edit(sysRoleEditParam));
    }

    @GetMapping("deleteById")
    @Operation(operationId = "deleteSysRole")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(sysRoleManager.deleteById(idParam));
    }

    @GetMapping("page")
    @Operation(operationId = "querySysRolePage")
    public Result<Pageable<SysRoleVO>> list(PageParam pageParam) {
        return Result.success(sysRoleManager.page(pageParam));
    }

    @GetMapping("queryPerms")
    @Operation(operationId = "queryPerms")
    public Result<List<PermVO>> queryPerms(IdParam idParam) {
        return Result.success(sysRoleManager.queryPerms(idParam));
    }

    @PostMapping("settingPerms")
    @Operation(operationId = "settingPerms")
    public Result<Boolean> settingPerms(@RequestBody @Validated SettingPermsParam settingPermsParam) {
        return Result.success(sysRoleManager.settingPerms(settingPermsParam));
    }

}
