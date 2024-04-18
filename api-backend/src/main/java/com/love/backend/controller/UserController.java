package com.love.backend.controller;

import com.love.backend.manager.UserManager;
import com.love.backend.model.param.UserEditParam;
import com.love.backend.model.param.UserQueryPageParam;
import com.love.backend.model.param.UserResetPasswordParam;
import com.love.backend.model.param.UserSaveParam;
import com.love.backend.model.vo.CustomerVO;
import com.love.backend.model.vo.UserDetailVO;
import com.love.backend.model.vo.UserVO;
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
@RequestMapping("user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "UserApi", description = "User manage operation")
public class UserController {

    private final UserManager userManager;

    @PostMapping("save")
    @Operation(operationId = "saveUser")
    public Result<UserVO> save(@RequestBody @Validated UserSaveParam userSaveParam) {
        return Result.success(userManager.save(userSaveParam));
    }

    @PostMapping("edit")
    @Operation(operationId = "editUser")
    public Result<UserVO> edit(@RequestBody @Validated UserEditParam userEditParam) {
        return Result.success(userManager.edit(userEditParam));
    }

    @GetMapping("detail")
    @Operation(operationId = "userDetail")
    public Result<UserDetailVO> detail(IdParam idParam) {
        return Result.success(userManager.detail(idParam));
    }

    @GetMapping("deleteById")
    @Operation(operationId = "deleteUser")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(userManager.deleteById(idParam));
    }

    @GetMapping("page")
    @Operation(operationId = "queryUserPage")
    public Result<Pageable<CustomerVO>> page(UserQueryPageParam userQueryPageParam) {
        return Result.success(userManager.page(userQueryPageParam));
    }

    @PostMapping("resetPassword")
    @Operation(operationId = "resetUserPassword")
    public Result<Boolean> resetPassword(@RequestBody @Validated UserResetPasswordParam userResetPasswordParam) {
        return Result.success(userManager.resetPassword(userResetPasswordParam));
    }
}
