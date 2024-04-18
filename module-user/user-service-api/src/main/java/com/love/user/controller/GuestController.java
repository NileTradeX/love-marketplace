package com.love.user.controller;

import com.love.common.param.IdsParam;
import com.love.common.result.Result;
import com.love.user.sdk.bo.GuestBO;
import com.love.user.sdk.dto.GuestDTO;
import com.love.user.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("guest")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GuestController {

    private final GuestService guestService;

    @PostMapping("saveOrUpdate")
    public Result<GuestDTO> saveOrUpdate(@RequestBody GuestBO guestBO) {
        return Result.success(guestService.saveOrUpdate(guestBO));
    }

    @GetMapping("queryByEmail")
    public Result<GuestDTO> queryByEmail(GuestBO guestBO) {
        return Result.success(guestService.saveOrUpdate(guestBO));
    }

    @PostMapping("queryByIdList")
    public Result<List<GuestDTO>> queryByIdList(@RequestBody IdsParam idsParam) {
        return Result.success(guestService.queryByIdList(idsParam));
    }
}
