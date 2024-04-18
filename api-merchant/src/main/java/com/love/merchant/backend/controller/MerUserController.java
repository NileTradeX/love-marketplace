package com.love.merchant.backend.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.merchant.backend.manager.MerUserManager;
import com.love.merchant.backend.model.param.*;
import com.love.merchant.backend.model.vo.LoginUserVO;
import com.love.merchant.backend.model.vo.MerUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("mer/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "MerUserApi", description = "all MerUser manage operation")
public class MerUserController {

    private final MerUserManager merUserManager;

    @PostMapping("save")
    @Operation(operationId = "saveMerUser")
    public Result<MerUserVO> save(@RequestBody @Validated MerUserSaveParam merUserSaveParam) {
        return Result.success(merUserManager.save(merUserSaveParam));
    }


    @PostMapping("edit")
    @Operation(operationId = "editMerUser")
    public Result<MerUserVO> edit(@RequestBody @Validated MerUserEditParam merUserEditParam) {
        return Result.success(merUserManager.edit(merUserEditParam));
    }


    @GetMapping("deleteById")
    @Operation(operationId = "deleteMerUser")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(merUserManager.deleteById(idParam));
    }

    @GetMapping("page")
    @Operation(operationId = "queryMerUserPage")
    public Result<Pageable<MerUserVO>> page(MerUserQueryPageParam merUserQueryPageParam) {
        return Result.success(merUserManager.page(merUserQueryPageParam));
    }

    @PostMapping("changePassword")
    @Operation(operationId = "changePassword")
    public Result<Boolean> changePassword(@RequestBody @Validated MerUserChangePasswordParam userChangePasswordParam) {
        return Result.success(merUserManager.changePassword(userChangePasswordParam));
    }

    @PostMapping("resetPassword")
    @Operation(operationId = "resetPassword")
    public Result<Boolean> resetPassword(@RequestBody @Validated MerUserResetPasswordParam merUserResetPasswordParam) {
        return Result.success(merUserManager.resetPassword(merUserResetPasswordParam));
    }

    @PostMapping("resetAdminPassword")
    @Operation(operationId = "resetAdminPassword")
    public Result<Boolean> resetAdminPassword(@RequestBody @Validated MerUserQueryByAccountParam merUserQueryByAccountParam) {
        return Result.success(merUserManager.resetAdminPassword(merUserQueryByAccountParam));
    }

    @PostMapping("doResetAdminPassword")
    @Operation(operationId = "doResetAdminPassword")
    public Result<Boolean> doResetAdminPassword(@RequestBody @Validated MerUserResetAdminPasswordParam merUserResetAdminPasswordParam) {
        return Result.success(merUserManager.doResetPassword(merUserResetAdminPasswordParam));
    }

    @PostMapping("login")
    @Operation(operationId = "login")
    public Result<LoginUserVO> login(@RequestBody @Validated MerUserLoginParam merUserLoginParam) {
        return Result.success(merUserManager.login(merUserLoginParam));
    }
}
