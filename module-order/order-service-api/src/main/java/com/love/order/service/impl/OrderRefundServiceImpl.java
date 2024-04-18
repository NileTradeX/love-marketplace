package com.love.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.order.bo.AfterSalesQueryBO;
import com.love.order.bo.OrderRefundBO;
import com.love.order.dto.OrderRefundDTO;
import com.love.order.entity.OrderRefundEntity;
import com.love.order.mapper.OrderRefundMapper;
import com.love.order.service.OrderRefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * (OrderRefund)表服务实现类
 *
 * @author eric
 * @since 2023-07-11 16:59:23
 */
@Service("orderRefundService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderRefundServiceImpl extends ServiceImpl<OrderRefundMapper, OrderRefundEntity> implements OrderRefundService {

    @Override
    public boolean save(OrderRefundEntity entity) {
        return super.save(entity);
    }

    @Override
    public OrderRefundEntity refund(OrderRefundBO refundBO) {
        OrderRefundEntity entity = new OrderRefundEntity()
                .setRefundNo(refundBO.getRefundNo())
                .setAfterSaleNo(refundBO.getAfterSaleNo())
                .setThirdRefundNo(refundBO.getThirdRefundNo())
                .setBuyerId(refundBO.getBuyerId())
                .setOrderNo(refundBO.getOrderNo())
                .setMerOrderNo(refundBO.getMerOrderNo())
                .setMerchantId(refundBO.getMerchantId())
                .setRefundAmount(refundBO.getRefundAmount())
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setRefundTime(LocalDateTime.now());
        saveOrUpdate(entity);
        return entity;
    }

    @Override
    public List<OrderRefundDTO> thirdList(AfterSalesQueryBO afterSalesQueryBO) {
        List<OrderRefundEntity> orderRefundEntityList = this.lambdaQuery().eq(OrderRefundEntity::getAfterSaleNo, afterSalesQueryBO.getAfterSaleNo()).list();
        return BeanUtil.copyToList(orderRefundEntityList, OrderRefundDTO.class);
    }
}

