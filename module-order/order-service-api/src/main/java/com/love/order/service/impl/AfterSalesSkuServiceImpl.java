package com.love.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.order.bo.AfterSalesQueryBO;
import com.love.order.bo.AfterSalesRecordQuerySkuBO;
import com.love.order.dto.AfterSalesSkuDTO;
import com.love.order.entity.AfterSalesSku;
import com.love.order.mapper.AfterSalesSkuMapper;
import com.love.order.service.AfterSalesSkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * (AfterSalesSku)表服务实现类
 *
 * @author eric
 * @since 2023-07-11 16:58:28
 */
@Service("afterSalesSkuService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AfterSalesSkuServiceImpl extends ServiceImpl<AfterSalesSkuMapper, AfterSalesSku> implements AfterSalesSkuService {

    @Override
    public boolean save(AfterSalesSku entity) {
        return super.save(entity);
    }

    @Override
    public List<AfterSalesSku> queryAfterSaleSkuList(List<String> afterSaleNoList) {
        return this.lambdaQuery().in(AfterSalesSku::getAfterSaleNo, afterSaleNoList).list();
    }

    @Override
    public List<AfterSalesSku> queryLastAfterSaleSkuList(String orderNo, String merOrderNo, List<AfterSalesRecordQuerySkuBO> querySkuBOList) {
        List<AfterSalesSku> afterSaleSkuList = this.lambdaQuery()
                .eq(Objects.nonNull(orderNo), AfterSalesSku::getOrderNo, orderNo)
                .eq(Objects.nonNull(merOrderNo), AfterSalesSku::getMerOrderNo, merOrderNo).list();
        if (CollectionUtil.isNotEmpty(querySkuBOList)) {
            afterSaleSkuList = afterSaleSkuList.stream().filter(t1 -> querySkuBOList.stream().anyMatch(t2 -> t1.getGoodsId().equals(t2.getGoodsId()) && t1.getSkuId().equals(t2.getSkuId())))
                    .collect(Collectors.toList());
        }

        return Lists.newArrayList(afterSaleSkuList.stream().collect(Collectors.toMap(t -> t.getGoodsId() + "_" + t.getSkuId(), Function.identity(), BinaryOperator.maxBy(Comparator.comparing(AfterSalesSku::getCreateTime)))).values());
    }

    @Override
    public boolean saveSkuBatch(List<AfterSalesSku> skuEntityList) {
        return this.saveBatch(skuEntityList);
    }

    @Override
    public List<AfterSalesSkuDTO> queryAfterSaleSku(AfterSalesQueryBO afterSalesQueryBO) {
        List<AfterSalesSku> afterSalesSkuEntityList = this.lambdaQuery().eq(AfterSalesSku::getAfterSaleNo, afterSalesQueryBO.getAfterSaleNo()).list();
        return BeanUtil.copyToList(afterSalesSkuEntityList, AfterSalesSkuDTO.class);
    }
}

