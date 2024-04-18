package com.love.goods.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.goods.bo.*;
import com.love.goods.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "goods-service-api", contextId = "goodsFeignClient", path = "goods")
public interface GoodsFeignClient {

    @PostMapping("savePhysical")
    Long savePhysical(PhysicalGoodsSaveBO physicalGoodsSaveBO);

    @GetMapping("detail")
    GoodsDTO detail(@SpringQueryMap GoodsDetailQueryBO goodsDetailQueryBO);

    @GetMapping("simple")
    GoodsSimpleDTO simple(@SpringQueryMap IdParam idParam);

    @GetMapping("deleteById")
    Boolean deleteById(@SpringQueryMap IdParam idParam);

    @GetMapping("page")
    Pageable<GoodsSimpleDTO> page(@SpringQueryMap GoodsQueryPageBO goodsQueryPageBO);

    @PostMapping("homepage")
    Pageable<GoodsHomepageDTO> homepage(GoodsHomepageQueryBO goodsHomePageQueryBO);

    @GetMapping("reviewPage")
    Pageable<GoodsReviewDTO> reviewPage(@SpringQueryMap GoodsReviewPageQueryBO goodsReviewPageQueryBO);

    @GetMapping("reviewStat")
    List<GoodsReviewStatDTO> reviewStat();

    @PostMapping("reviewApprove")
    Boolean reviewApprove(GoodsReviewApproveBO goodsReviewApproveBO);

    @PostMapping("reviewReject")
    Boolean reviewReject(GoodsReviewRejectBO goodsReviewRejectBO);

    @GetMapping("putOff")
    Boolean putOff(@SpringQueryMap IdParam idParam);

    @GetMapping("putOn")
    Boolean putOn(@SpringQueryMap IdParam idParam);

    @PostMapping("duplicate")
    Long duplicate(GoodsDuplicateBO goodsDuplicateBO);

    @PostMapping("modifySalesVolume")
    Boolean modifySalesVolume(UpdateGoodsSalesVolumeBO goodsSalesVolumeUpdateBO);

    @PostMapping("influencerGoodsPage")
    Pageable<InfGoodsPageDTO> influencerGoodsPage(InfGoodsPageQueryBO influencerGoodsPageQueryBO);

    @GetMapping("simpleInfluenceGoods")
    InfGoodsSimpleDTO simpleInfluenceGoods(@SpringQueryMap IdParam idParam);

    @GetMapping("queryByIds")
    List<GoodsDTO> queryByIds(@SpringQueryMap IdsParam idsParam);

    @GetMapping("queryForHomepageByIds")
    List<GoodsHomepageDTO> queryForHomepageByIds(@SpringQueryMap IdsParam idsParam);

    @PostMapping("queryRecommendGoodsByCategory")
    List<RecommendGoodsDTO> queryRecommendGoodsByCategory(GoodsRecommendQueryBO goodsRecommendQueryBO);

    @GetMapping("popularPage")
    List<GoodsHomepageDTO> popularPage(@SpringQueryMap GoodsStatusPageQueryBO goodsStatusPageQueryBO);

    @GetMapping("queryTopXSales")
    List<GoodsSimpleDTO> queryTopXSales(@RequestParam(name = "num") Integer num);
}
