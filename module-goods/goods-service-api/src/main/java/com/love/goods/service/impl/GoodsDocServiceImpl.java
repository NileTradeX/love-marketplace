package com.love.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.goods.dto.GoodsDocDTO;
import com.love.goods.entity.GoodsDoc;
import com.love.goods.mapper.GoodsDocMapper;
import com.love.goods.service.GoodsDocService;
import org.springframework.stereotype.Service;


@Service
public class GoodsDocServiceImpl extends ServiceImpl<GoodsDocMapper, GoodsDoc> implements GoodsDocService {

    @Override
    public boolean saveOrUpdate(GoodsDoc entity) {
        return super.saveOrUpdate(entity);
    }

    @Override
    public GoodsDocDTO queryByGoodsId(Long goodsId) {
        return BeanUtil.copyProperties(this.lambdaQuery().eq(GoodsDoc::getGoodsId, goodsId).one(), GoodsDocDTO.class);
    }
}

