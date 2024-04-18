package com.love.merchant.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.merchant.bo.MerUserAdminInvitationEditBO;
import com.love.merchant.bo.MerUserAdminInvitationQueryByCodeBO;
import com.love.merchant.bo.MerUserAdminInvitationQueryPageBO;
import com.love.merchant.bo.MerUserAdminInvitationSaveBO;
import com.love.merchant.dto.MerUserAdminInvitationDTO;
import com.love.merchant.service.MerUserAdminInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mer/user/invitation")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerUserInvitationController {

    private final MerUserAdminInvitationService merUserInvitationService;

    @PostMapping("save")
    public Result<MerUserAdminInvitationDTO> save(@RequestBody MerUserAdminInvitationSaveBO merUserAdminInvitationSaveBO) {
        return Result.success(merUserInvitationService.save(merUserAdminInvitationSaveBO));
    }

    @PostMapping("edit")
    public Result<Boolean> edit(@RequestBody MerUserAdminInvitationEditBO merUserAdminInvitationEditBO) {
        return Result.success(merUserInvitationService.edit(merUserAdminInvitationEditBO));
    }

    @GetMapping("queryById")
    public Result<MerUserAdminInvitationDTO> queryById(IdParam idParam) {
        return Result.success(merUserInvitationService.queryById(idParam));
    }

    @GetMapping("page")
    public Result<Pageable<MerUserAdminInvitationDTO>> page(MerUserAdminInvitationQueryPageBO merUserAdminInvitationQueryPageBO) {
        return Result.success(merUserInvitationService.page(merUserAdminInvitationQueryPageBO));
    }

    @GetMapping("code")
    public Result<MerUserAdminInvitationDTO> queryByCode(MerUserAdminInvitationQueryByCodeBO merUserAdminInvitationQueryByCodeBO) {
        return Result.success(merUserInvitationService.queryByCode(merUserAdminInvitationQueryByCodeBO));
    }
}
