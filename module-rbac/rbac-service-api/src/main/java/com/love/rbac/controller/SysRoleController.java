package com.love.rbac.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.rbac.bo.SettingPermsBO;
import com.love.rbac.bo.SysRoleEditBO;
import com.love.rbac.bo.SysRoleQueryPageBO;
import com.love.rbac.bo.SysRoleSaveBO;
import com.love.rbac.dto.SysPermDTO;
import com.love.rbac.dto.SysRoleDTO;
import com.love.rbac.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("sys/role")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @PostMapping("save")
    public Result<SysRoleDTO> save(@RequestBody SysRoleSaveBO sysRoleSaveBO) {
        return Result.success(sysRoleService.save(sysRoleSaveBO));
    }

    @PostMapping("edit")
    public Result<SysRoleDTO> edit(@RequestBody SysRoleEditBO sysRoleEditBO) {
        return Result.success(sysRoleService.edit(sysRoleEditBO));
    }

    @GetMapping("queryById")
    public Result<SysRoleDTO> queryById(IdParam idParam) {
        return Result.success(sysRoleService.queryById(idParam));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(sysRoleService.deleteById(idParam));
    }

    @GetMapping("page")
    public Result<Pageable<SysRoleDTO>> page(SysRoleQueryPageBO queryListBO) {
        return Result.success(sysRoleService.page(queryListBO));
    }

    @GetMapping("queryPerms")
    public Result<List<SysPermDTO>> queryPerms(@RequestParam(value = "roleId", required = false) Long roleId) {
        return Result.success(sysRoleService.queryPerms(roleId));
    }

    @PostMapping("settingPerms")
    public Result<Boolean> settingPerms(@RequestBody SettingPermsBO settingPermsBO) {
        return Result.success(sysRoleService.settingPerms(settingPermsBO));
    }
}
