package com.love.merchant.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageableUtil;
import com.love.goods.bo.*;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.client.GoodsSkuFeignClient;
import com.love.goods.dto.GoodsDTO;
import com.love.goods.dto.GoodsSimpleDTO;
import com.love.merchant.backend.model.param.GoodsDuplicateParam;
import com.love.merchant.backend.model.param.GoodsQueryPageParam;
import com.love.merchant.backend.model.param.ModifyGoodsSkuAvailableStockParam;
import com.love.merchant.backend.model.param.PhysicalGoodsSaveParam;
import com.love.merchant.backend.model.vo.GoodsSimpleVO;
import com.love.merchant.backend.model.vo.GoodsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GoodsManager {

    private final GoodsFeignClient goodsFeignClient;
    private final GoodsSkuFeignClient goodsSkuFeignClient;
    private final MerchantIdManager merchantIdManager;

    public Long savePhysical(PhysicalGoodsSaveParam physicalGoodsSaveParam) {
        PhysicalGoodsSaveBO physicalGoodsSaveBO = BeanUtil.copyProperties(physicalGoodsSaveParam, PhysicalGoodsSaveBO.class);
        physicalGoodsSaveBO.setMerchantId(merchantIdManager.getMerchantId(physicalGoodsSaveParam.getUserId()));
        return goodsFeignClient.savePhysical(physicalGoodsSaveBO);
    }

    public GoodsVO detail(IdParam idParam) {
        GoodsDTO goodsDTO = goodsFeignClient.detail(GoodsDetailQueryBO.builder().id(idParam.getId()).build());
        return BeanUtil.copyProperties(goodsDTO, GoodsVO.class);
    }

    public Boolean deleteById(IdParam idParam) {
        return goodsFeignClient.deleteById(idParam);
    }

    public Pageable<GoodsSimpleVO> page(GoodsQueryPageParam goodsQueryPageParam) {
        GoodsQueryPageBO goodsQueryPageBO = BeanUtil.copyProperties(goodsQueryPageParam, GoodsQueryPageBO.class);
        goodsQueryPageBO.setMerchantId(merchantIdManager.getMerchantId(goodsQueryPageParam.getUserId()));
        Pageable<GoodsSimpleDTO> page = goodsFeignClient.page(goodsQueryPageBO);
        return PageableUtil.toPage(page, GoodsSimpleVO.class);
    }

    public Boolean putOff(IdParam idParam) {
        return goodsFeignClient.putOff(idParam);
    }

    public Boolean putOn(IdParam idParam) {
        return goodsFeignClient.putOn(idParam);
    }

    public Long duplicate(GoodsDuplicateParam goodsDuplicateParam) {
        GoodsDuplicateBO goodsDuplicateBO = BeanUtil.copyProperties(goodsDuplicateParam, GoodsDuplicateBO.class);
        return goodsFeignClient.duplicate(goodsDuplicateBO);
    }

    public Boolean modifyGoodsSkuAvailableStock(ModifyGoodsSkuAvailableStockParam modifyGoodsSkuAvailableStockParam) {
        if (CollectionUtil.isEmpty(modifyGoodsSkuAvailableStockParam.getModifySkuAvailableStockList())) {
            return true;
        }
        ModifyGoodsSkuAvailableStockBO modifyGoodsSkuAvailableStockBO = BeanUtil.copyProperties(modifyGoodsSkuAvailableStockParam, ModifyGoodsSkuAvailableStockBO.class);
        return goodsSkuFeignClient.modifyAvailableStock(modifyGoodsSkuAvailableStockBO);
    }
}
