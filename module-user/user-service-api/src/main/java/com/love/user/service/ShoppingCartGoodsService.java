package com.love.user.service;

import com.love.common.param.ByUserIdParam;
import com.love.common.param.IdParam;
import com.love.user.sdk.bo.ShoppingCartGoodsMergeBO;
import com.love.user.sdk.bo.ShoppingCartGoodsQueryListBO;
import com.love.user.sdk.bo.ShoppingCartGoodsSaveBO;
import com.love.user.sdk.dto.ShoppingCartGoodsDTO;

import java.util.List;

public interface ShoppingCartGoodsService {
    Boolean saveOrUpdate(ShoppingCartGoodsSaveBO shoppingCartGoodsSaveBO);

    List<ShoppingCartGoodsDTO> queryList(ShoppingCartGoodsQueryListBO shoppingCartGoodsQueryListBO);

    Boolean deleteById(ByUserIdParam idParam);

    Boolean clearByUserId(IdParam idParam);

    Boolean mergeGoods(ShoppingCartGoodsMergeBO shoppingCartGoodsMergeBO);

}
