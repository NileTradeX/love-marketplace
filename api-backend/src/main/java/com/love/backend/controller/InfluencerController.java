package com.love.backend.controller;

import com.love.backend.manager.InfluencerManager;
import com.love.backend.model.param.InfOrderQueryPageParam;
import com.love.backend.model.param.InfUserQueryPageParam;
import com.love.backend.model.param.InfUserSaveParam;
import com.love.backend.model.vo.InfUserOrderVO;
import com.love.backend.model.vo.InfUserInfoVO;
import com.love.backend.model.vo.InfUserVO;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("influencer")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "InfluencerApi", description = "Influencer manage operation")
public class InfluencerController {

    private final InfluencerManager influencerManager;

    @PostMapping("preSave")
    public Result<InfUserVO> preSave(@RequestBody InfUserSaveParam infUserSaveParam) {
        return Result.success(influencerManager.preSave(infUserSaveParam));
    }

    @GetMapping("page")
    public Result<Pageable<InfUserVO>> page(InfUserQueryPageParam infUserPageQueryBO) {
        return Result.success(influencerManager.page(infUserPageQueryBO));
    }

    @GetMapping("detail")
    public Result<InfUserInfoVO> detail(IdParam idParam) {
        return Result.success(influencerManager.detail(idParam));
    }

    @GetMapping("active")
    public Result<Boolean> active(IdParam idParam) {
        return Result.success(influencerManager.active(idParam));
    }

    @GetMapping("deactive")
    public Result<Boolean> deactive(IdParam idParam) {
        return Result.success(influencerManager.deactive(idParam));
    }

    @GetMapping("orders")
    public Result<Pageable<InfUserOrderVO>> orders(InfOrderQueryPageParam infOrderQueryPageParam) {
        return Result.success(influencerManager.orders(infOrderQueryPageParam));
    }
}
