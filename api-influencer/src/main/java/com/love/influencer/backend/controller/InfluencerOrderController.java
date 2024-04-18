package com.love.influencer.backend.controller;

import com.love.common.page.Pageable;
import com.love.common.result.Result;
import com.love.influencer.backend.manager.InfluencerOrderManager;
import com.love.influencer.backend.model.param.InfUserOrderQueryPageParam;
import com.love.influencer.backend.model.param.InfUserOrderSaveParam;
import com.love.influencer.backend.model.vo.InfUserOrderVO;
import com.love.influencer.dto.InfUserOrderDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("influencer/order")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "InfluencerOrderApi", description = "all Influencer Order manage operation")
public class InfluencerOrderController {

    private final InfluencerOrderManager influencerOrderManager;

    @PostMapping("save")
    public Result<Boolean> save(@RequestBody InfUserOrderSaveParam infUserOrderSaveParam) {
        return Result.success(influencerOrderManager.save(infUserOrderSaveParam));
    }

    @GetMapping("page")
    public Result<Pageable<InfUserOrderVO>> page(InfUserOrderQueryPageParam infUserOrderQueryPageParam) {
        return Result.success(influencerOrderManager.page(infUserOrderQueryPageParam));
    }
}
