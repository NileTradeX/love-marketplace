package com.love.merchant.controller;

import com.love.common.result.Result;
import com.love.merchant.bo.*;
import com.love.merchant.dto.MerUserAdminBusinessInfoDTO;
import com.love.merchant.service.MerUserAdminBusinessInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mer/businessInfo")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerBusinessInfoController {

    private final MerUserAdminBusinessInfoService merUserAdminBusinessInfoService;

    @PostMapping("changeBizOrderMgmtEmail")
    public Result<Boolean> changeBizOrderMgmtEmail(@RequestBody MerUserAdminBusinessInfoChangeEmailBO changeEmailBO) {
        return Result.success(merUserAdminBusinessInfoService.changeBizOrderMgmtEmail(changeEmailBO));
    }

    @PostMapping("edit")
    public Result<Boolean> edit(@RequestBody MerUserAdminBusinessInfoEditBO merUserAdminBusinessInfoEditBO) {
        return Result.success(merUserAdminBusinessInfoService.updateById(merUserAdminBusinessInfoEditBO));
    }

    @GetMapping("queryByAdminId")
    public Result<MerUserAdminBusinessInfoDTO> queryByAdminId(MerQueryByAdminIdBO merQueryByAdminIdBO) {
        return Result.success(merUserAdminBusinessInfoService.queryById(merQueryByAdminIdBO.getAdminId()));
    }

    @GetMapping("all")
    public Result<List<MerUserAdminBusinessInfoDTO>> all() {
        return Result.success(merUserAdminBusinessInfoService.all());
    }

    @PostMapping("queryByBizName")
    public Result<MerUserAdminBusinessInfoDTO> queryByBizName(@RequestBody MerUserAdminBusinessInfoQueryByBizName queryByBizName) {
        return Result.success(merUserAdminBusinessInfoService.queryByBizName(queryByBizName));
    }
}
