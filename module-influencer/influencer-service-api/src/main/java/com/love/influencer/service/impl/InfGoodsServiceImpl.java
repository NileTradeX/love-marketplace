package com.love.influencer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageUtil;
import com.love.influencer.bo.*;
import com.love.influencer.dto.*;
import com.love.influencer.entity.InfGoods;
import com.love.influencer.entity.InfUser;
import com.love.influencer.enums.GoodsSortType;
import com.love.influencer.enums.GoodsStatus;
import com.love.influencer.mapper.InfGoodsMapper;
import com.love.influencer.service.InfGoodsService;
import com.love.influencer.service.InfUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfGoodsServiceImpl extends ServiceImpl<InfGoodsMapper, InfGoods> implements InfGoodsService {

    public static final int MAX_RECOMMENDING_SIZE = 30;
    private final InfUserService infUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchSave(InfGoodsBatchSaveBO infGoodsBatchSaveBO) {
        if (GoodsStatus.RECOMMENDED.getStatus() == infGoodsBatchSaveBO.getStatus() && CollectionUtil.isNotEmpty(infGoodsBatchSaveBO.getSelectedGoods())) {
            Long count = this.countRecommendedGoodsByInfluencerId(infGoodsBatchSaveBO.getInfluencerId());
            if (count + infGoodsBatchSaveBO.getSelectedGoods().size() > MAX_RECOMMENDING_SIZE) {
                throw BizException.build("We only allow recommending up to %d products for now. Sorry for the inconvenience!", MAX_RECOMMENDING_SIZE);
            }
        }

        InfUserInfoDTO infUser = infUserService.queryById(IdParam.builder().id(infGoodsBatchSaveBO.getInfluencerId()).build());
        List<InfGoods> selectedGoods = BeanUtil.copyToList(infGoodsBatchSaveBO.getSelectedGoods(), InfGoods.class);
        selectedGoods.forEach(o -> {
            o.setStatus(infGoodsBatchSaveBO.getStatus());
            o.setInfluencerId(infGoodsBatchSaveBO.getInfluencerId());
            if (Objects.isNull(o.getCommissionRate())) {
                o.setCommissionRate(infUser.getCommissionRate());
            }
        });
        return this.saveBatch(selectedGoods);
    }

    @Override
    public Pageable<InfGoodsDTO> page(InfGoodsQueryPageBO infGoodsQueryPageBO) {
        QueryChainWrapper<InfGoods> queryChainWrapper = this.query()
                .eq("influencer_id", infGoodsQueryPageBO.getInfluencerId())
                .eq(Objects.nonNull(infGoodsQueryPageBO.getStatus()), "status", infGoodsQueryPageBO.getStatus());
        if (Objects.isNull(infGoodsQueryPageBO.getGoodsSortType())) {
            queryChainWrapper.orderBy(true, false, "update_time");
        } else {
            if (GoodsSortType.NEWEST.getSortType() == infGoodsQueryPageBO.getGoodsSortType()) {
                queryChainWrapper.orderBy(true, false, "update_time");
            } else if (GoodsSortType.COMMUNITY_SCORE.getSortType() == infGoodsQueryPageBO.getGoodsSortType()) {
                queryChainWrapper.orderBy(true, false, "community_score");
            } else if (GoodsSortType.SALES_VOLUME.getSortType() == infGoodsQueryPageBO.getGoodsSortType()) {
                queryChainWrapper.orderBy(true, false, "sales_volume");
            } else queryChainWrapper.orderBy(true, GoodsSortType.PRICE_HIGH_TO_LOW.getSortType() != infGoodsQueryPageBO.getGoodsSortType(), "min_price");
        }
        Page<InfGoods> page = queryChainWrapper.page(new Page<>(infGoodsQueryPageBO.getPageNum(), infGoodsQueryPageBO.getPageSize()));
        InfUser influencerUser = infUserService.queryInfluencerById(IdParam.builder().id(infGoodsQueryPageBO.getInfluencerId()).build());
        return PageUtil.toPage(page, InfGoodsDTO.class, (src, dst) -> dst.setInfluencerCode(Objects.nonNull(influencerUser) ? influencerUser.getCode() : StringUtils.EMPTY));
    }

    @Override
    public Boolean updateStatus(InfGoodsUpdateStatusBO infGoodsUpdateStatusBO) {
        Long count = this.countRecommendedGoodsByInfluencerId(infGoodsUpdateStatusBO.getInfluencerId());
        //一个达人只能推荐30个商品
        if (GoodsStatus.RECOMMENDED.getStatus() == infGoodsUpdateStatusBO.getStatus() && count + infGoodsUpdateStatusBO.getIds().size() > MAX_RECOMMENDING_SIZE) {
            throw BizException.build("We only allow recommending up to %d products for now. Sorry for the inconvenience!", MAX_RECOMMENDING_SIZE);
        }
        return this.lambdaUpdate().in(InfGoods::getId, infGoodsUpdateStatusBO.getIds())
                .set(InfGoods::getStatus, infGoodsUpdateStatusBO.getStatus())
                .set(InfGoods::getUpdateTime, LocalDateTime.now())
                .update();
    }

    @Override
    public InfGoodsStatusCountDTO statusCount(InfGoodsStatusCountBO infGoodsStatusCountQueryBO) {
        List<InfGoods> influencerGoods = this.lambdaQuery()
                .select(InfGoods::getStatus)
                .eq(InfGoods::getInfluencerId, infGoodsStatusCountQueryBO.getInfluencerId()).list();
        if (CollectionUtil.isNotEmpty(influencerGoods)) {
            Long toBeRecommended = influencerGoods.stream().filter(o -> GoodsStatus.TO_BE_RECOMMENDED.getStatus() == o.getStatus()).count();
            Long recommendedCount = influencerGoods.stream().filter(o -> GoodsStatus.RECOMMENDED.getStatus() == o.getStatus()).count();
            Long invalidCount = influencerGoods.stream().filter(o -> GoodsStatus.INVALID.getStatus() == o.getStatus()).count();
            Long totalCount = toBeRecommended + recommendedCount + invalidCount;
            return InfGoodsStatusCountDTO.builder()
                    .toBeRecommended(toBeRecommended)
                    .recommendedCount(recommendedCount)
                    .invalidCount(invalidCount)
                    .totalCount(totalCount)
                    .build();
        }
        return null;
    }

    @Override
    public InfGoodsSimpleDTO queryByGoodsIdAndInfluencerId(InfGoodsCommissionQueryBO infGoodsCommissionQueryBO) {
        Optional<InfGoods> goods = this.lambdaQuery().select
                        (InfGoods::getId, InfGoods::getGoodsId, InfGoods::getInfluencerId, InfGoods::getCommissionRate)
                .eq(InfGoods::getInfluencerId, infGoodsCommissionQueryBO.getInfluencerId())
                .eq(InfGoods::getGoodsId, infGoodsCommissionQueryBO.getGoodsId()).list().stream().findFirst();
        if (!goods.isPresent()) {
            throw BizException.build("Influencer Goods not found!");
        }
        return BeanUtil.copyProperties(goods.get(), InfGoodsSimpleDTO.class);
    }

    @Override
    public List<InfGoodsIdDTO> queryGoodsIdByInfluencerId(InfGoodsQueryListBO infGoodsQueryListBO) {
        List<InfGoods> goodsList = this.lambdaQuery().select(InfGoods::getGoodsId).eq(InfGoods::getInfluencerId, infGoodsQueryListBO.getInfluencerId()).list();
        return BeanUtil.copyToList(goodsList, InfGoodsIdDTO.class);
    }

    @Override
    public Long countRecommendedGoodsByInfluencerId(Long influencerId) {
        return this.lambdaQuery().eq(InfGoods::getStatus, GoodsStatus.RECOMMENDED.getStatus()).eq(InfGoods::getInfluencerId, influencerId).count();
    }

    @Override
    public Boolean updateGoodsById(InfGoodsUpdateByIdBO infGoodsUpdateByIdBO) {
        return this.lambdaUpdate()
                .set(Objects.nonNull(infGoodsUpdateByIdBO.getStatus()), InfGoods::getStatus, infGoodsUpdateByIdBO.getStatus())
                .set(Objects.nonNull(infGoodsUpdateByIdBO.getGoodsStatus()), InfGoods::getGoodsStatus, infGoodsUpdateByIdBO.getGoodsStatus())
                .set(Objects.nonNull(infGoodsUpdateByIdBO.getMinPrice()), InfGoods::getMinPrice, infGoodsUpdateByIdBO.getMinPrice())
                .set(Objects.nonNull(infGoodsUpdateByIdBO.getMaxPrice()), InfGoods::getMaxPrice, infGoodsUpdateByIdBO.getMaxPrice())
                .set(Objects.nonNull(infGoodsUpdateByIdBO.getCommunityScore()), InfGoods::getCommunityScore, infGoodsUpdateByIdBO.getCommunityScore())
                .eq(InfGoods::getGoodsId, infGoodsUpdateByIdBO.getGoodsId()).update();
    }

    @Override
    public Boolean updateGoodsAvailableStock(InfGoodsStockUpdateBO infGoodsStockUpdateBO) {
        return this.lambdaUpdate().set(InfGoods::getAvailableStock, infGoodsStockUpdateBO.getAvailableStock()).eq(InfGoods::getGoodsId, infGoodsStockUpdateBO.getGoodsId()).update();
    }

    public Boolean modifySalesVolume(InfGoodsSalesVolumeUpdateBO infGoodsSalesVolumeUpdateBO) {
        return this.lambdaUpdate().setSql("sales_volume = sales_volume + " + infGoodsSalesVolumeUpdateBO.getQty()).eq(InfGoods::getGoodsId, infGoodsSalesVolumeUpdateBO.getGoodsId()).update();
    }
}
