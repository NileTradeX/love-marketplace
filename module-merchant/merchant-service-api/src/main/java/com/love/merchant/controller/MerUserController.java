package com.love.merchant.controller;

import com.love.common.Constants;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.merchant.bo.*;
import com.love.merchant.dto.*;
import com.love.merchant.service.MerUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mer/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerUserController {

    private final MerUserService merUserService;

    @PostMapping("save")
    public Result<MerUserDTO> save(@RequestBody MerUserSaveBO merUserSaveBO, @RequestHeader(Constants.USER_ID) Long adminId) {
        merUserSaveBO.setGroupId(merUserService.queryGroupId(adminId));
        return Result.success(merUserService.save(merUserSaveBO));
    }

    @PostMapping("saveAdmin")
    public Result<MerUserDTO> saveAdmin(@RequestBody MerUserAdminSaveBO merUserAdminSaveBO) {
        return Result.success(merUserService.saveAdmin(merUserAdminSaveBO));
    }

    @PostMapping("edit")
    public Result<MerUserDTO> edit(@RequestBody MerUserEditBO merUserEditBO) {
        return Result.success(merUserService.edit(merUserEditBO));
    }

    @PostMapping("editAdmin")
    public Result<MerUserDTO> editAdmin(@RequestBody MerUserAdminSaveBO merUserAdminSaveBO) {
        return Result.success(merUserService.editAdmin(merUserAdminSaveBO));
    }

    @GetMapping("queryById")
    public Result<MerUserDTO> queryById(IdParam idParam) {
        return Result.success(merUserService.queryById(idParam));
    }

    @GetMapping("queryAdminById")
    public Result<MerUserAdminDTO> queryAdminById(MerUserAdminQueryBO merUserAdminQueryBO) {
        return Result.success(merUserService.queryAdminById(merUserAdminQueryBO));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(merUserService.deleteById(idParam));
    }

    @GetMapping("page")
    public Result<Pageable<MerUserDTO>> page(MerUserQueryPageBO merUserQueryPageBO, @RequestHeader(Constants.USER_ID) Long adminId) {
        merUserQueryPageBO.setGroupId(merUserService.queryGroupId(adminId));
        return Result.success(merUserService.page(merUserQueryPageBO));
    }

    @GetMapping("simpleAdminPage")
    public Result<Pageable<MerUserDTO>> simpleAdminPage(MerUserQueryAdminPageBO merUserQueryAdminPageBO) {
        return Result.success(merUserService.simpleAdminPage(merUserQueryAdminPageBO));
    }

    @GetMapping("fullAdminPage")
    public Result<Pageable<MerUserAdminDTO>> fullAdminPage(MerUserQueryAdminPageBO merUserQueryAdminPageBO) {
        return Result.success(merUserService.fullAdminPage(merUserQueryAdminPageBO));
    }

    @PostMapping("queryByAccount")
    public Result<MerUserDTO> queryByAccount(@RequestBody MerUserQueryByAccountBO merUserQueryByAccountBO) {
        return Result.success(merUserService.queryByAccount(merUserQueryByAccountBO));
    }

    @PostMapping("changePassword")
    public Result<Boolean> changePassword(@RequestBody MerUserChangePasswordBO merUserChangePasswordBO) {
        return Result.success(merUserService.changePassword(merUserChangePasswordBO));
    }

    @PostMapping("resetPassword")
    public Result<Boolean> resetPassword(@RequestBody MerUserResetPasswordBO sysUserResetPasswordBO) {
        return Result.success(merUserService.resetPassword(sysUserResetPasswordBO));
    }

    @PostMapping("login")
    public Result<LoginUserDTO> login(@RequestBody MerUserLoginBO sysUserLoginBO) {
        return Result.success(merUserService.login(sysUserLoginBO));
    }

    @PostMapping("approve")
    public Result<MerUserAdminApproveDTO> approve(@RequestBody MerUserAdminApproveBO merUserAdminApproveBO) {
        return Result.success(merUserService.approve(merUserAdminApproveBO));
    }

    @PostMapping("reject")
    public Result<MerUserAdminRejectDTO> reject(@RequestBody MerUserAdminRejectBO merUserAdminRejectBO) {
        return Result.success(merUserService.reject(merUserAdminRejectBO));
    }

    @GetMapping("review/stat")
    public Result<MerUserAdminReviewStatDTO> reviewStat(MerUserAdminReviewStatBO merUserAdminReviewStatBO) {
        return Result.success(merUserService.reviewStat(merUserAdminReviewStatBO));
    }
}
