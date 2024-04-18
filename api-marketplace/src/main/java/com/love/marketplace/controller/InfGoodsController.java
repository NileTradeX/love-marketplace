package com.love.marketplace.controller;

import com.love.common.page.Pageable;
import com.love.common.result.Result;
import com.love.influencer.dto.InfGoodsDTO;
import com.love.marketplace.manager.InfGoodsManager;
import com.love.marketplace.model.param.InfGoodsQueryPageParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("influencer/goods")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "InfluencerGoodsApi", description = "all Influencer Goods manage operation")
public class InfGoodsController {

    private final InfGoodsManager infGoodsManager;

    @GetMapping("page")
    public Result<Pageable<InfGoodsDTO>> page(InfGoodsQueryPageParam infGoodsQueryPageParam) {
        return Result.success(infGoodsManager.page(infGoodsQueryPageParam));
    }
}
