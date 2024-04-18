package com.love.influencer.client;

import com.love.common.page.Pageable;
import com.love.influencer.bo.*;
import com.love.influencer.dto.InfGoodsDTO;
import com.love.influencer.dto.InfGoodsIdDTO;
import com.love.influencer.dto.InfGoodsSimpleDTO;
import com.love.influencer.dto.InfGoodsStatusCountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "influencer-service-api", contextId = "infGoodsFeignClient", path = "influencer/goods")
public interface InfGoodsFeignClient {

    @PostMapping("batchSave")
    Boolean batchSave(InfGoodsBatchSaveBO influencerGoodsBatchSaveBO);

    @GetMapping("page")
    Pageable<InfGoodsDTO> page(@SpringQueryMap InfGoodsQueryPageBO influencerGoodsQueryPageBO);

    @PostMapping("updateStatus")
    Boolean updateStatus(InfGoodsUpdateStatusBO infGoodsUpdateStatusBO);

    @GetMapping("statusCount")
    InfGoodsStatusCountDTO statusCount(@SpringQueryMap InfGoodsStatusCountBO infGoodsStatusCountQueryBO);

    @GetMapping("queryByGoodsIdAndInfluencerId")
    InfGoodsSimpleDTO queryByGoodsIdAndInfluencerId(@SpringQueryMap InfGoodsCommissionQueryBO infGoodsCommissionQueryBO);

    @GetMapping("queryGoodsIdByInfluencerId")
    List<InfGoodsIdDTO> queryGoodsIdByInfluencerId(@SpringQueryMap InfGoodsQueryListBO infGoodsQueryBO);

    @PostMapping("updateById")
    Boolean updateById(InfGoodsUpdateByIdBO infGoodsStatusUpdateBO);

    @PostMapping("updateGoodsAvailableStock")
    Boolean updateGoodsAvailableStock(InfGoodsStockUpdateBO infGoodsStockUpdateBO);

    @PostMapping("modifySalesVolume")
    Boolean modifySalesVolume(InfGoodsSalesVolumeUpdateBO infGoodsSalesVolumeUpdateBO);
}
