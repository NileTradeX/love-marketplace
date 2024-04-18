package com.love.influencer.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.influencer.bo.*;
import com.love.influencer.dto.InfUserDTO;
import com.love.influencer.dto.InfUserInfoDTO;
import com.love.influencer.service.InfUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("influencer")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfUserController {

    private final InfUserService infUserService;

    @PostMapping("save")
    public Result<InfUserDTO> save(@RequestBody InfUserSaveBO infUserSaveBO) {
        return Result.success(infUserService.save(infUserSaveBO));
    }

    @PostMapping("edit")
    public Result<Boolean> edit(@RequestBody InfUserEditBO infUserEditBO) {
        return Result.success(infUserService.edit(infUserEditBO));
    }

    @GetMapping("queryById")
    public Result<InfUserInfoDTO> queryById(IdParam idParam) {
        return Result.success(infUserService.queryById(idParam));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(infUserService.deleteById(idParam));
    }

    @GetMapping("page")
    public Result<Pageable<InfUserDTO>> page(InfUserQueryPageBO infUserQueryPageBO) {
        return Result.success(infUserService.page(infUserQueryPageBO));
    }

    @PostMapping("changePassword")
    public Result<Boolean> changePassword(@RequestBody InfUserChangePasswordBO infUserChangePasswordBO) {
        return Result.success(infUserService.changePassword(infUserChangePasswordBO));
    }

    @PostMapping("resetPassword")
    public Result<Boolean> resetPassword(@RequestBody InfUserResetPasswordBO infUserResetPasswordBO) {
        return Result.success(infUserService.resetPassword(infUserResetPasswordBO));
    }

    @PostMapping("login")
    public Result<InfUserDTO> login(@RequestBody InfUserLoginBO infUserLoginBO) {
        return Result.success(infUserService.login(infUserLoginBO));
    }

    @PostMapping("changeAvatar")
    public Result<Boolean> changeAvatar(@RequestBody InfUserChangeAvatarBO infUserChangeAvatarBO) {
        return Result.success(infUserService.changeAvatar(infUserChangeAvatarBO));
    }

    @GetMapping("queryByCode")
    public Result<InfUserDTO> queryByCode(InfUserQueryByCodeBO infUserQueryByCodeBO) {
        return Result.success(infUserService.queryByCode(infUserQueryByCodeBO));
    }

    @GetMapping("updateStatus")
    public Result<Boolean> updateStatus(InfUserUpdateStatusBO infUserUpdateStatusBO) {
        return Result.success(infUserService.updateStatus(infUserUpdateStatusBO));
    }
}
