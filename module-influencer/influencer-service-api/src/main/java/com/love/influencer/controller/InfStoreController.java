package com.love.influencer.controller;

import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.influencer.bo.*;
import com.love.influencer.dto.GoodsSortTypeDTO;
import com.love.influencer.dto.InfStoreDTO;
import com.love.influencer.dto.InfStoreIdDTO;
import com.love.influencer.service.InfStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("influencer/store")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfStoreController {

    private final InfStoreService infStoreService;

    @PostMapping("save")
    public Result<InfStoreDTO> save(@RequestBody InfStoreSaveBO infStoreSaveBO) {
        return Result.success(infStoreService.save(infStoreSaveBO));
    }

    @PostMapping("edit")
    public Result<InfStoreDTO> edit(@RequestBody InfStoreEditBO infStoreEditBO) {
        return Result.success(infStoreService.edit(infStoreEditBO));
    }

    @GetMapping("queryById")
    public Result<InfStoreDTO> queryById(IdParam idParam) {
        return Result.success(infStoreService.queryById(idParam));
    }

    @PostMapping("changeCover")
    public Result<Boolean> changeCover(@RequestBody InfStoreChangeCoverBO infStoreChangeCoverBO) {
        return Result.success(infStoreService.changeCover(infStoreChangeCoverBO));
    }

    @GetMapping("queryGoodSortTypes")
    public Result<List<GoodsSortTypeDTO>> queryGoodSortTypes() {
        return Result.success(infStoreService.queryGoodSortTypes());
    }

    @GetMapping("queryByDisplayName")
    public Result<InfStoreDTO> queryByDisplayName(InfStoreQueryByDisplayNameBO infStoreQueryByDisplayNameBO) {
        return Result.success(infStoreService.queryByDisplayName(infStoreQueryByDisplayNameBO));
    }

    @GetMapping("queryStoreIdByInfluencerId")
    public Result<InfStoreIdDTO> queryStoreIdByInfluencerId(InfStoreQueryByInfIdBO infStoreQueryByInfIdBO) {
        return Result.success(infStoreService.queryStoreIdByInfluencerId(infStoreQueryByInfIdBO));
    }
}
