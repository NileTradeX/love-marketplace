package com.love.marketplace.manager;

import cn.hutool.core.bean.BeanUtil;
import com.github.slugify.Slugify;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.dto.InfGoodsSimpleDTO;
import com.love.influencer.bo.InfGoodsQueryPageBO;
import com.love.influencer.bo.InfStoreQueryByInfIdBO;
import com.love.influencer.client.InfGoodsFeignClient;
import com.love.influencer.client.InfStoreFeignClient;
import com.love.influencer.dto.InfGoodsDTO;
import com.love.influencer.dto.InfStoreIdDTO;
import com.love.marketplace.model.param.InfGoodsQueryPageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfGoodsManager {

    private final Slugify slugify = new Slugify().withLowerCase(true).withUnderscoreSeparator(false);
    private final InfGoodsFeignClient infGoodsFeignClient;
    private final GoodsFeignClient goodsFeignClient;
    private final InfStoreFeignClient infStoreFeignClient;

    public Pageable<InfGoodsDTO> page(InfGoodsQueryPageParam infGoodsQueryPageParam) {
        InfGoodsQueryPageBO infGoodsQueryPageBO = BeanUtil.copyProperties(infGoodsQueryPageParam, InfGoodsQueryPageBO.class);
        InfStoreIdDTO infStoreIdDTO = infStoreFeignClient.queryStoreIdByInfluencerId(InfStoreQueryByInfIdBO.builder().influencerId(infGoodsQueryPageParam.getInfluencerId()).build());
        if (Objects.nonNull(infStoreIdDTO)) {
            infGoodsQueryPageBO.setGoodsSortType(infStoreIdDTO.getGoodsSortType());
        }
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
        return pageable;
    }

}
