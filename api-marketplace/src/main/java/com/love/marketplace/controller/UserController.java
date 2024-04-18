package com.love.marketplace.controller;

import com.love.common.result.Result;
import com.love.marketplace.manager.UserManager;
import com.love.marketplace.model.param.*;
import com.love.marketplace.model.vo.LoginUserVO;
import com.love.marketplace.model.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "UserApi")
public class UserController {

    private final UserManager userManager;

    @PostMapping("register")
    @Operation(operationId = "userRegister")
    public Result<LoginUserVO> register(@RequestBody @Validated UserRegisterParam userRegisterParam) {
        return Result.success(userManager.register(userRegisterParam));
    }

    @PostMapping("edit")
    @Operation(operationId = "editUser")
    public Result<UserVO> edit(@RequestBody @Validated UserEditParam accountSaveParam) {
        return Result.success(userManager.edit(accountSaveParam));
    }

    @GetMapping("detail")
    @Operation(operationId = "userDetail")
    public Result<UserVO> detail(UserIdParam userIdParam) {
        return Result.success(userManager.detail(userIdParam));
    }

    @PostMapping("login")
    @Operation(operationId = "login")
    public Result<LoginUserVO> login(@RequestBody @Validated UserLoginParam userLoginParam) {
        return Result.success(userManager.login(userLoginParam));
    }

    @PostMapping("loginWithBolt")
    @Operation(operationId = "loginWithBolt")
    public Result<LoginUserVO> loginWithBolt(@RequestBody @Validated UserLoginWithBoltParam userLoginWithBoltParam) {
        return Result.success(userManager.loginWithBolt(userLoginWithBoltParam));
    }

    @PostMapping("changePassword")
    @Operation(operationId = "changePassword")
    public Result<Boolean> changePassword(@RequestBody @Validated UserChangePasswordParam userChangePasswordParam) {
        return Result.success(userManager.changePassword(userChangePasswordParam));
    }

    @PostMapping("resetPassword")
    @Operation(operationId = "resetPassword")
    public Result<Boolean> resetPassword(@RequestBody @Validated UserQueryByEmailParam userQueryByEmailParam) {
        return Result.success(userManager.sendResetPasswordEmail(userQueryByEmailParam));
    }

    @PostMapping("doResetPassword")
    @Operation(operationId = "doResetPassword")
    public Result<Boolean> doResetPassword(@RequestBody @Validated UserResetPasswordParam userResetPasswordParam) {
        return Result.success(userManager.doResetPassword(userResetPasswordParam));
    }

    @GetMapping("verifyEmail")
    @Operation(operationId = "verifyEmail")
    public Result<LoginUserVO> verifyEmail(TokenParam tokenParam) {
        return Result.success(userManager.verifyEmail(tokenParam));
    }

    @GetMapping("resendActiveEmail")
    @Operation(operationId = "resendActiveEmail")
    public Result<Boolean> resendActiveEmail(TokenParam tokenParam) {
        return Result.success(userManager.resendActiveEmail(tokenParam));
    }

    @PostMapping("changeAvatar")
    @Operation(operationId = "changeAvatar")
    public Result<Boolean> changeAvatar(@RequestBody @Validated UserChangeAvatarParam userChangeAvatarParam) {
        return Result.success(userManager.changeAvatar(userChangeAvatarParam));
    }

    @GetMapping("exist")
    @Operation(operationId = "userExist")
    public Result<Boolean> exist(@Validated UserExistParam userExistParam) {
        return Result.success(userManager.exist(userExistParam));
    }

}
