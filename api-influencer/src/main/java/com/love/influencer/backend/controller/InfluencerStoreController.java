package com.love.influencer.backend.controller;

import com.love.common.param.ByUserIdParam;
import com.love.common.result.Result;
import com.love.influencer.backend.manager.InfluencerStoreManager;
import com.love.influencer.backend.model.param.InfStoreChangeCoverParam;
import com.love.influencer.backend.model.param.InfStoreEditParam;
import com.love.influencer.backend.model.param.InfStoreQueryByDisplayNameParam;
import com.love.influencer.backend.model.param.InfStoreSaveParam;
import com.love.influencer.backend.model.vo.GoodsSortTypeVO;
import com.love.influencer.backend.model.vo.InfStoreVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("influencer/store")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "InfluencerStoreApi", description = "all Influencer Store manage operation")
public class InfluencerStoreController {

    private final InfluencerStoreManager influencerStoreManager;

    @PostMapping("save")
    public Result<InfStoreVO> save(@RequestBody @Validated InfStoreSaveParam infStoreSaveParam) {
        return Result.success(influencerStoreManager.save(infStoreSaveParam));
    }

    @PostMapping("edit")
    public Result<InfStoreVO> edit(@RequestBody InfStoreEditParam infStoreEditParam) {
        return Result.success(influencerStoreManager.edit(infStoreEditParam));
    }

    @GetMapping("queryById")
    public Result<InfStoreVO> queryById(ByUserIdParam byUserIdParam) {
        return Result.success(influencerStoreManager.queryById(byUserIdParam));
    }

    @PostMapping("changeCover")
    public Result<Boolean> changeCover(@RequestBody @Validated InfStoreChangeCoverParam storeChangeCoverParam) {
        return Result.success(influencerStoreManager.changeCover(storeChangeCoverParam));
    }

    @GetMapping("queryGoodSortTypes")
    public Result<List<GoodsSortTypeVO>> queryGoodSortTypes() {
        return Result.success(influencerStoreManager.queryGoodSortTypes());
    }

    @GetMapping("queryByInfluencerId")
    public Result<InfStoreVO> queryByInfluencerId(InfStoreQueryByDisplayNameParam influencerStoreQueryParam) {
        return Result.success(influencerStoreManager.queryByDisplayName(influencerStoreQueryParam));
    }
}
