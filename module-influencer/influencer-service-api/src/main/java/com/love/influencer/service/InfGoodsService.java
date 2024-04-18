package com.love.influencer.service;

import com.love.common.page.Pageable;
import com.love.influencer.bo.*;
import com.love.influencer.dto.InfGoodsDTO;
import com.love.influencer.dto.InfGoodsIdDTO;
import com.love.influencer.dto.InfGoodsSimpleDTO;
import com.love.influencer.dto.InfGoodsStatusCountDTO;

import java.util.List;

public interface InfGoodsService {

    Boolean batchSave(InfGoodsBatchSaveBO infGoodsBatchSaveBO);

    Pageable<InfGoodsDTO> page(InfGoodsQueryPageBO infGoodsQueryPageBO);

    Boolean updateStatus(InfGoodsUpdateStatusBO infGoodsUpdateStatusBO);

    InfGoodsStatusCountDTO statusCount(InfGoodsStatusCountBO infGoodsStatusCountQueryBO);

    InfGoodsSimpleDTO queryByGoodsIdAndInfluencerId(InfGoodsCommissionQueryBO infGoodsCommissionQueryBO);

    List<InfGoodsIdDTO> queryGoodsIdByInfluencerId(InfGoodsQueryListBO infGoodsQueryListBO);

    Long countRecommendedGoodsByInfluencerId(Long influencerId);

    Boolean updateGoodsById(InfGoodsUpdateByIdBO infGoodsStatusUpdateBO);

    Boolean updateGoodsAvailableStock(InfGoodsStockUpdateBO infGoodsStockUpdateBO);

    Boolean modifySalesVolume(InfGoodsSalesVolumeUpdateBO infGoodsSalesVolumeUpdateBO);
}
