package com.love.merchant.controller;

import com.love.common.Constants;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.merchant.bo.MerRoleEditBO;
import com.love.merchant.bo.MerRoleQueryPageBO;
import com.love.merchant.bo.MerRoleSaveBO;
import com.love.merchant.bo.SettingPermsBO;
import com.love.merchant.dto.MerPermDTO;
import com.love.merchant.dto.MerRoleDTO;
import com.love.merchant.service.MerRoleService;
import com.love.merchant.service.MerUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("mer/role")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerRoleController {

    private final MerRoleService merRoleService;
    private final MerUserService merUserService;


    @PostMapping("save")
    public Result<MerRoleDTO> save(@RequestBody MerRoleSaveBO sysRoleSaveBO, @RequestHeader(Constants.USER_ID) Long adminId) {
        sysRoleSaveBO.setGroupId(merUserService.queryGroupId(adminId));
        return Result.success(merRoleService.save(sysRoleSaveBO));
    }


    @PostMapping("edit")
    public Result<MerRoleDTO> edit(@RequestBody MerRoleEditBO sysRoleEditBO) {
        return Result.success(merRoleService.edit(sysRoleEditBO));
    }


    @GetMapping("queryById")
    public Result<MerRoleDTO> queryById(IdParam idParam) {
        return Result.success(merRoleService.queryById(idParam));
    }


    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(merRoleService.deleteById(idParam));
    }


    @GetMapping("page")
    public Result<Pageable<MerRoleDTO>> page(MerRoleQueryPageBO merRoleQueryPageBO, @RequestHeader(Constants.USER_ID) Long adminId) {
        merRoleQueryPageBO.setGroupId(merUserService.queryGroupId(adminId));
        return Result.success(merRoleService.page(merRoleQueryPageBO));
    }


    @GetMapping("queryPerms")
    public Result<List<MerPermDTO>> queryPerms(@RequestParam(value = "roleId", required = false) Long roleId) {
        return Result.success(merRoleService.queryPerms(roleId));
    }


    @PostMapping("settingPerms")
    public Result<Boolean> settingPerms(@RequestBody SettingPermsBO settingPermsBO) {
        return Result.success(merRoleService.settingPerms(settingPermsBO));
    }
}
