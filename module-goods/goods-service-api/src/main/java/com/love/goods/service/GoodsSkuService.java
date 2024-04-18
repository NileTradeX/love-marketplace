package com.love.goods.service;

import com.love.goods.bo.GoodsSkuSaveBO;
import com.love.goods.bo.ModifyDefaultSkuBO;
import com.love.goods.bo.ModifyGoodsSkuAvailableStockBO;
import com.love.goods.bo.ModifyGoodsSkuCommittedStockBO;
import com.love.goods.dto.GoodsSkuDTO;
import com.love.goods.dto.GoodsSkuSimpleDTO;

import java.util.List;
import java.util.Map;

public interface GoodsSkuService {

    List<Long> batchSave(Long goodsId, List<GoodsSkuSaveBO> skuBOList);

    boolean deleteByGoodsId(Long goodsId);

    boolean deleteByIds(Long[] ids);

    List<Long> duplicateSkus(Long oldGoodsId, Long newGoodsId);

    List<GoodsSkuDTO> queryByGoodsId(Long goodsId, boolean withDeleted);

    List<GoodsSkuDTO> queryByIds(List<Long> skuIdList);

    GoodsSkuDTO queryById(Long id);

    GoodsSkuSimpleDTO simple(Long id);

    Boolean modifyCommittedStock(ModifyGoodsSkuCommittedStockBO modifyGoodsSkuCommittedStockBO);

    Boolean modifyAvailableStock(ModifyGoodsSkuAvailableStockBO modifyGoodsSkuAvailableStockBO);

    Integer queryAvailableStockByGoodsId(Long goodsId);

    Boolean modifyDefaultSku(ModifyDefaultSkuBO modifyDefaultSkuBO);

    Map<Integer, Long> queryAvailableStockByGoodsIds(List<Long> goodsIds);

    Map<Long, String> querySkuSpecs(List<Long> skuIds);
}
