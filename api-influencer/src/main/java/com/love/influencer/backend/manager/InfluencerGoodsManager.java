package com.love.influencer.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.github.slugify.Slugify;
import com.love.common.page.Pageable;
import com.love.common.param.ByUserIdParam;
import com.love.common.param.IdParam;
import com.love.common.util.PageableUtil;
import com.love.goods.bo.InfGoodsPageQueryBO;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.client.GoodsSkuFeignClient;
import com.love.goods.dto.InfGoodsPageDTO;
import com.love.goods.dto.InfGoodsSimpleDTO;
import com.love.influencer.backend.model.param.InfGoodsBatchSaveParam;
import com.love.influencer.backend.model.param.InfGoodsPageQueryParam;
import com.love.influencer.backend.model.param.InfGoodsQueryPageParam;
import com.love.influencer.backend.model.param.InfGoodsUpdateStatusParam;
import com.love.influencer.backend.model.vo.InfGoodsPageVO;
import com.love.influencer.backend.model.vo.InfGoodsStatusCountVO;
import com.love.influencer.backend.model.vo.InfGoodsVO;
import com.love.influencer.bo.*;
import com.love.influencer.client.InfGoodsFeignClient;
import com.love.influencer.dto.InfGoodsDTO;
import com.love.influencer.dto.InfGoodsIdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfluencerGoodsManager {

    private final Slugify slugify = new Slugify().withLowerCase(true).withUnderscoreSeparator(false);
    private final InfGoodsFeignClient infGoodsFeignClient;
    private final GoodsFeignClient goodsFeignClient;
    private final GoodsSkuFeignClient goodsSkuFeignClient;

    public Boolean batchSave(InfGoodsBatchSaveParam infGoodsBatchSaveParam) {
        InfGoodsBatchSaveBO infGoodsBatchSaveBO = BeanUtil.copyProperties(infGoodsBatchSaveParam, InfGoodsBatchSaveBO.class);
        infGoodsBatchSaveBO.setInfluencerId(infGoodsBatchSaveParam.getUserId());
        return infGoodsFeignClient.batchSave(infGoodsBatchSaveBO);
    }

    public Pageable<InfGoodsVO> page(InfGoodsQueryPageParam infGoodsQueryPageParam) {
        InfGoodsQueryPageBO infGoodsQueryPageBO = BeanUtil.copyProperties(infGoodsQueryPageParam, InfGoodsQueryPageBO.class);
        infGoodsQueryPageBO.setInfluencerId(infGoodsQueryPageParam.getUserId());
        Pageable<InfGoodsDTO> pageable = infGoodsFeignClient.page(infGoodsQueryPageBO);
        if (pageable.getTotal() > 0) {
            for (InfGoodsDTO goods : pageable.getRecords()) {
                InfGoodsSimpleDTO simpleGoods = goodsFeignClient.simpleInfluenceGoods(IdParam.builder().id(goods.getGoodsId()).build());
                if (Objects.nonNull(simpleGoods)) {
                    goods.setTitle(simpleGoods.getTitle());
                    goods.setSubTitle(simpleGoods.getSubTitle());
                    goods.setWhiteBgImg(simpleGoods.getWhiteBgImg());
                    goods.setSlug(slugify.slugify(simpleGoods.getTitle()) + "_" + goods.getGoodsId() + "?influencerCode=" + goods.getInfluencerCode());
                }
            }
        }
        return PageableUtil.toPage(pageable, InfGoodsVO.class);
    }

    public Boolean updateStatus(InfGoodsUpdateStatusParam infGoodsUpdateStatusParam) {
        InfGoodsUpdateStatusBO infGoodsUpdateStatusBO = BeanUtil.copyProperties(infGoodsUpdateStatusParam, InfGoodsUpdateStatusBO.class);
        infGoodsUpdateStatusBO.setInfluencerId(infGoodsUpdateStatusParam.getUserId());
        return infGoodsFeignClient.updateStatus(infGoodsUpdateStatusBO);
    }

    public Pageable<InfGoodsPageVO> influencerGoodsPage(InfGoodsPageQueryParam infGoodsPageQueryParam) {
        InfGoodsPageQueryBO infGoodsPageQueryBO = BeanUtil.copyProperties(infGoodsPageQueryParam, InfGoodsPageQueryBO.class);
        infGoodsPageQueryBO.setInfluencerId(infGoodsPageQueryParam.getUserId());
        infGoodsPageQueryBO.setGoodsId(infGoodsFeignClient.queryGoodsIdByInfluencerId(InfGoodsQueryListBO.builder().influencerId(infGoodsPageQueryParam.getUserId()).build()).stream().mapToLong(InfGoodsIdDTO::getGoodsId).boxed().collect(Collectors.toList()));
        Pageable<InfGoodsPageDTO> pageable = goodsFeignClient.influencerGoodsPage(infGoodsPageQueryBO);
        if (pageable.getTotal() > 0) {
            for (InfGoodsPageDTO goods : pageable.getRecords()) {
                goods.setAvailableStock(goodsSkuFeignClient.queryAvailableStockByGoodsId(IdParam.builder().id(goods.getId()).build()));
            }
        }
        return PageableUtil.toPage(pageable, InfGoodsPageVO.class, (src, dst) -> dst.setSlug(slugify.slugify(src.getTitle()) + "_" + src.getId()));
    }

    public InfGoodsStatusCountVO statusCount(ByUserIdParam byUserIdParam) {
        InfGoodsStatusCountBO infGoodsStatusCountQueryBO = new InfGoodsStatusCountBO();
        infGoodsStatusCountQueryBO.setInfluencerId(byUserIdParam.getUserId());
        return BeanUtil.copyProperties(infGoodsFeignClient.statusCount(infGoodsStatusCountQueryBO), InfGoodsStatusCountVO.class);
    }

}
