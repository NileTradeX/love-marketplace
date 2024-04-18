package com.love.goods.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.goods.bo.*;
import com.love.goods.dto.*;

import java.util.List;

public interface GoodsService {
    Long savePhysical(PhysicalGoodsSaveBO physicalGoodsSaveBO);

    GoodsDTO detail(GoodsDetailQueryBO goodsDetailQueryBO);

    GoodsSimpleDTO simple(IdParam idParam);

    boolean deleteById(IdParam idParam);

    Pageable<GoodsSimpleDTO> page(GoodsQueryPageBO goodsQueryPageBO);

    Pageable<GoodsHomepageDTO> homepage(GoodsHomepageQueryBO goodsHomepageQueryBO);

    Pageable<GoodsReviewDTO> reviewPage(GoodsReviewPageQueryBO goodsReviewPageQueryBO);

    List<GoodsReviewStatDTO> reviewStat();

    boolean reviewApprove(GoodsReviewApproveBO goodsReviewApproveBO);

    boolean reviewReject(GoodsReviewRejectBO goodsReviewRejectBO);

    boolean putOff(IdParam idParam);

    boolean putOn(IdParam idParam);

    Long duplicate(GoodsDuplicateBO goodsDuplicateBO);

    Long countByBrandId(Long id);

    boolean modifyOnHandStock(Long goodsId, int differStock);

    boolean modifySalesVolume(UpdateGoodsSalesVolumeBO goodsSalesVolumeUpdateBO);

    long countByCategoryId(Long cateId, Integer cateLevel);

    Pageable<InfGoodsPageDTO> influencerGoodsPage(InfGoodsPageQueryBO influencerGoodsPageQueryBO);

    InfGoodsSimpleDTO simpleInfluenceGoods(IdParam idParam);

    List<GoodsDTO> queryByIds(IdsParam idsParam);

    List<GoodsHomepageDTO> queryForHomepageByIds(IdsParam idsParam);

    List<RecommendGoodsDTO> queryRecommendGoodsByCategory(GoodsRecommendQueryBO goodsRecommendQueryBO);

    List<GoodsHomepageDTO> popularPage(GoodsStatusPageQueryBO goodsStatusPageQueryBO);

    List<GoodsSimpleDTO> queryTopXSales(Integer num);
}
