package com.love.goods.service;

import com.love.goods.dto.GoodsDocDTO;
import com.love.goods.entity.GoodsDoc;

public interface GoodsDocService {
    boolean saveOrUpdate(GoodsDoc goodsDoc);

    GoodsDocDTO queryByGoodsId(Long goodsId);
}

