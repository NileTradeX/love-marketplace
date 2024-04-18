package com.love.influencer.backend.controller;

import com.love.common.page.Pageable;
import com.love.common.param.ByUserIdParam;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.influencer.backend.manager.InfluencerUserManager;
import com.love.influencer.backend.model.param.*;
import com.love.influencer.backend.model.vo.InfUserInfoVO;
import com.love.influencer.backend.model.vo.InfUserVO;
import com.love.influencer.backend.model.vo.LoginUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("influencer")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "InfluencerUserApi", description = "all Influencer User manage operation")
public class InfluencerUserController {

    private final InfluencerUserManager influencerUserManager;

    @PostMapping("save")
    public Result<InfUserVO> save(@RequestBody @Validated InfUserSaveParam infUserSaveParam) {
        return Result.success(influencerUserManager.save(infUserSaveParam));
    }

    @PostMapping("edit")
    public Result<Boolean> edit(@RequestBody InfUserEditParam infUserEditParam) {
        return Result.success(influencerUserManager.edit(infUserEditParam));
    }

    @GetMapping("queryById")
    public Result<InfUserInfoVO> queryById(ByUserIdParam byUserIdParam) {
        return Result.success(influencerUserManager.queryById(byUserIdParam));
    }

    @GetMapping("queryByCode")
    public Result<InfUserVO> queryByCode(InfUserQueryByCodeParam infUserQueryByCodeParam) {
        return Result.success(influencerUserManager.queryByCode(infUserQueryByCodeParam));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(influencerUserManager.deleteById(idParam));
    }

    @GetMapping("page")
    public Result<Pageable<InfUserVO>> page(InfUserQueryPageParam influencerUserQueryPageParam) {
        return Result.success(influencerUserManager.page(influencerUserQueryPageParam));
    }

    @PostMapping("changePassword")
    public Result<Boolean> changePassword(@RequestBody InfUserChangePasswordParam influencerUserChangePasswordParam) {
        return Result.success(influencerUserManager.changePassword(influencerUserChangePasswordParam));
    }

    @PostMapping("resetPassword")
    public Result<Boolean> resetPassword(@RequestBody InfUserResetPasswordParam influencerUserResetPasswordParam) {
        return Result.success(influencerUserManager.resetPassword(influencerUserResetPasswordParam));
    }

    @PostMapping("login")
    @Operation(operationId = "login")
    public Result<LoginUserVO> login(@RequestBody @Validated InfUserLoginParam influencerUserLoginParam) {
        return Result.success(influencerUserManager.login(influencerUserLoginParam));
    }

    @PostMapping("changeAvatar")
    public Result<Boolean> changeAvatar(@RequestBody @Validated InfUserChangeAvatarParam influencerChangeAvatarParam) {
        return Result.success(influencerUserManager.changeAvatar(influencerChangeAvatarParam));
    }

    @PostMapping("register")
    public Result<Boolean> register(@RequestBody InfRegisterParam infRegisterParam) {
        return Result.success(influencerUserManager.register(infRegisterParam));
    }

}
