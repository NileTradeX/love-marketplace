package com.love.backend.controller;

import com.love.backend.manager.SysUserManager;
import com.love.backend.model.param.*;
import com.love.backend.model.vo.LoginUserVO;
import com.love.backend.model.vo.SysUserVO;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("sys/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "SysUserApi", description = "all SysUser manage operation")
public class SysUserController {

    private final SysUserManager sysUserManager;

    @PostMapping("save")
    @Operation(operationId = "saveSysUser")
    public Result<SysUserVO> save(@RequestBody @Validated SysUserSaveParam sysUserSaveParam) {
        return Result.success(sysUserManager.save(sysUserSaveParam));
    }

    @PostMapping("edit")
    @Operation(operationId = "editSysUser")
    public Result<SysUserVO> edit(@RequestBody @Validated SysUserEditParam sysUserEditParam) {
        return Result.success(sysUserManager.edit(sysUserEditParam));
    }

    @GetMapping("deleteById")
    @Operation(operationId = "deleteSysUser")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(sysUserManager.deleteById(idParam));
    }

    @GetMapping("page")
    @Operation(operationId = "querySysUserPage")
    public Result<Pageable<SysUserVO>> page(SysUserQueryPageParam sysUserQueryPageParam) {
        return Result.success(sysUserManager.page(sysUserQueryPageParam));
    }

    @PostMapping("changePassword")
    @Operation(operationId = "changeSysUserPassword")
    public Result<Boolean> changePassword(@RequestBody @Validated SysUserChangePasswordParam sysUserChangePasswordParam) {
        return Result.success(sysUserManager.changePassword(sysUserChangePasswordParam));
    }

    @PostMapping("login")
    @Operation(operationId = "login")
    public Result<LoginUserVO> login(@RequestBody @Validated SysUserLoginParam sysUserLoginParam) {
        return Result.success(sysUserManager.login(sysUserLoginParam));
    }
}
