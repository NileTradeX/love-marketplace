package com.love.user.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.common.result.Result;
import com.love.user.sdk.bo.*;
import com.love.user.sdk.dto.UserDTO;
import com.love.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

    private final UserService userService;

    @PostMapping("save")
    public Result<UserDTO> save(@RequestBody UserSaveBO userSaveBO) {
        return Result.success(userService.save(userSaveBO));
    }

    @PostMapping("edit")
    public Result<UserDTO> edit(@RequestBody UserEditBO sysUserEditBO) {
        return Result.success(userService.edit(sysUserEditBO));
    }

    @GetMapping("queryById")
    public Result<UserDTO> queryById(IdParam idParam) {
        return Result.success(userService.queryById(idParam, false));
    }

    @GetMapping("simple")
    public Result<UserDTO> simple(IdParam idParam) {
        return Result.success(userService.queryById(idParam, true));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(userService.deleteById(idParam));
    }

    @GetMapping("page")
    public Result<Pageable<UserDTO>> page(UserQueryPageBO queryPageBO) {
        return Result.success(userService.page(queryPageBO));
    }

    @GetMapping("queryByEmail")
    public Result<UserDTO> queryByEmail(UserQueryByEmailBO userQueryByEmailBO) {
        return Result.success(userService.queryByEmail(userQueryByEmailBO));
    }

    @PostMapping("login")
    public Result<UserDTO> login(@RequestBody UserLoginBO userLoginBO) {
        return Result.success(userService.login(userLoginBO));
    }

    @PostMapping("changePassword")
    public Result<Boolean> changePassword(@RequestBody UserChangePasswordBO userChangePasswordBO) {
        return Result.success(userService.changePassword(userChangePasswordBO));
    }

    @PostMapping("resetPassword")
    public Result<Boolean> resetPassword(@RequestBody UserResetPasswordBO userResetPasswordBO) {
        return Result.success(userService.resetPassword(userResetPasswordBO));
    }

    @GetMapping("verifyEmail")
    public Result<Boolean> verifyEmail(IdParam idParam) {
        return Result.success(userService.verifyEmail(idParam));
    }

    @PostMapping("changeAvatar")
    public Result<Boolean> changeAvatar(@RequestBody UserChangeAvatarBO userChangeAvatarBO) {
        return Result.success(userService.changeAvatar(userChangeAvatarBO));
    }

    @PostMapping("queryByIdList")
    public Result<List<UserDTO>> queryByIdList(@RequestBody IdsParam idsParam) {
        return Result.success(userService.queryByIdList(idsParam));
    }
}
