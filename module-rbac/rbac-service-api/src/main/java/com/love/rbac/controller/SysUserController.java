package com.love.rbac.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.rbac.bo.*;
import com.love.rbac.dto.LoginUserDTO;
import com.love.rbac.dto.SysUserDTO;
import com.love.rbac.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sys/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysUserController {

    private final SysUserService sysUserService;

    @PostMapping("save")
    public Result<SysUserDTO> save(@RequestBody SysUserSaveBO sysUserSaveBO) {
        return Result.success(sysUserService.save(sysUserSaveBO));
    }

    @PostMapping("edit")
    public Result<SysUserDTO> edit(@RequestBody SysUserEditBO sysUserEditBO) {
        return Result.success(sysUserService.edit(sysUserEditBO));
    }

    @GetMapping("queryById")
    public Result<SysUserDTO> queryById(IdParam idParam) {
        return Result.success(sysUserService.queryById(idParam));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(sysUserService.deleteById(idParam));
    }

    @GetMapping("page")
    public Result<Pageable<SysUserDTO>> page(SysUserQueryPageBO sysUserQueryPageBO) {
        return Result.success(sysUserService.page(sysUserQueryPageBO));
    }

    @PostMapping("changePassword")
    public Result<Boolean> changePassword(@RequestBody SysUserChangePasswordBO sysUserResetPasswordBO) {
        return Result.success(sysUserService.changePassword(sysUserResetPasswordBO));
    }

    @PostMapping("login")
    public Result<LoginUserDTO> login(@RequestBody SysUserLoginBO sysUserLoginBO) {
        return Result.success(sysUserService.login(sysUserLoginBO));
    }

}
