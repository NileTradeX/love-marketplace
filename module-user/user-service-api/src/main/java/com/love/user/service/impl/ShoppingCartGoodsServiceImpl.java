package com.love.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.param.ByUserIdParam;
import com.love.common.param.IdParam;
import com.love.user.entity.ShoppingCartGoods;
import com.love.user.mapper.ShoppingCartGoodsMapper;
import com.love.user.sdk.bo.ShoppingCartGoodsMergeBO;
import com.love.user.sdk.bo.ShoppingCartGoodsQueryListBO;
import com.love.user.sdk.bo.ShoppingCartGoodsSaveBO;
import com.love.user.sdk.dto.ShoppingCartGoodsDTO;
import com.love.user.service.ShoppingCartGoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingCartGoodsServiceImpl extends ServiceImpl<ShoppingCartGoodsMapper, ShoppingCartGoods> implements ShoppingCartGoodsService {

    @Override
    @Transactional
    public Boolean saveOrUpdate(ShoppingCartGoodsSaveBO shoppingCartGoodsSaveBO) {
        // Quantities have been merged at the api layer,then delete old
        if(Objects.nonNull(shoppingCartGoodsSaveBO.getId())){
            this.lambdaUpdate()
                    .eq(ShoppingCartGoods::getUserId, shoppingCartGoodsSaveBO.getUserId())
                    .eq(ShoppingCartGoods::getGoodsId, shoppingCartGoodsSaveBO.getGoodsId())
                    .eq(ShoppingCartGoods::getSkuId, shoppingCartGoodsSaveBO.getSkuId())
                    .remove();
        }
        return this.saveOrUpdate(BeanUtil.copyProperties(shoppingCartGoodsSaveBO, ShoppingCartGoods.class));
    }

    @Override
    public List<ShoppingCartGoodsDTO> queryList(ShoppingCartGoodsQueryListBO shoppingCartGoodsQueryListBO) {
        List<ShoppingCartGoods> shoppingCartGoodsList = this.lambdaQuery()
                .eq(ShoppingCartGoods::getUserId, shoppingCartGoodsQueryListBO.getUserId())
                .eq(Objects.nonNull(shoppingCartGoodsQueryListBO.getGoodsId()),ShoppingCartGoods::getGoodsId, shoppingCartGoodsQueryListBO.getGoodsId())
                .eq(Objects.nonNull(shoppingCartGoodsQueryListBO.getSkuId()),ShoppingCartGoods::getSkuId, shoppingCartGoodsQueryListBO.getSkuId())
                .list();
        if (CollectionUtils.isEmpty(shoppingCartGoodsList)) {
            return Collections.emptyList();
        }
        return BeanUtil.copyToList(shoppingCartGoodsList, ShoppingCartGoodsDTO.class);
    }

    @Override
    public Boolean deleteById(ByUserIdParam idParam) {
        return this.lambdaUpdate().eq(ShoppingCartGoods::getUserId, idParam.getUserId()).eq(ShoppingCartGoods::getId, idParam.getId()).remove();
    }

    @Override
    public Boolean clearByUserId(IdParam idParam) {
        return this.lambdaUpdate().eq(ShoppingCartGoods::getUserId, idParam.getId()).remove();
    }

    @Override
    @Transactional
    public Boolean mergeGoods(ShoppingCartGoodsMergeBO shoppingCartGoodsMergeBO) {
        Long userId = shoppingCartGoodsMergeBO.getUserId();
        List<ShoppingCartGoods> result=new ArrayList<>();
        Map<Long, ShoppingCartGoods> oldMap = this.lambdaQuery().eq(ShoppingCartGoods::getUserId, userId).list().stream()
                .collect(Collectors.toMap(e -> e.getSkuId(), e -> e, (o1, o2) -> o1));

        List<ShoppingCartGoods> newList = shoppingCartGoodsMergeBO.getItemList().stream().map(
                item -> ShoppingCartGoods.builder()
                        .userId(userId).goodsId(item.getGoodsId()).skuId(item.getSkuId())
                        .price(item.getPrice()).qty(item.getQty()).influencerCode(item.getInfluencerCode())
                .build()).collect(Collectors.toList()
        );
        for (ShoppingCartGoods newItem : newList) {
            ShoppingCartGoods oldItem = oldMap.get(newItem.getSkuId());
            if (Objects.nonNull(oldItem)) {
                //merge qty
                oldItem.setQty(oldItem.getQty() + newItem.getQty());
                //refresh price
                oldItem.setPrice(newItem.getPrice());
                oldItem.setInfluencerCode(newItem.getInfluencerCode());
                result.add(oldItem);
            } else {
                result.add(newItem);
            }
        }
        this.saveOrUpdateBatch(result);
        return Boolean.TRUE;
    }
}
