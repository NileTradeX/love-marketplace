package com.love.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.util.PageUtil;
import com.love.order.bo.UserQueryOrderPageBO;
import com.love.order.dto.MerchantOrderDTO;
import com.love.order.dto.OrderItemDTO;
import com.love.order.entity.MerchantOrder;
import com.love.order.entity.OrderItem;
import com.love.order.enums.BuyerType;
import com.love.order.mapper.MerchantOrderMaper;
import com.love.order.service.MerchantOrderService;
import com.love.order.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerchantOrderServiceImpl extends ServiceImpl<MerchantOrderMaper, MerchantOrder> implements MerchantOrderService {

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public boolean save(MerchantOrder entity) {
        return super.save(entity);
    }

    @Override
    public List<MerchantOrder> queryByOrderId(Long orderId) {
        return this.lambdaQuery().eq(MerchantOrder::getOrderId, orderId).list();
    }

    @Override
    public boolean updateStatusByOrderId(Long orderId, Integer status, List<Long> skuIdList, String reason) {
        if (CollUtil.isEmpty(skuIdList)) {
            return this.lambdaUpdate().eq(MerchantOrder::getOrderId, orderId).set(MerchantOrder::getStatus, status).set(Objects.nonNull(reason), MerchantOrder::getReason, reason).set(MerchantOrder::getUpdateTime, LocalDateTime.now()).update();
        }

        Set<Long> idList = orderItemService.queryMerchantOrderId(skuIdList);
        if (CollUtil.isNotEmpty(idList)) {
            return this.lambdaUpdate().eq(MerchantOrder::getOrderId, orderId).in(MerchantOrder::getId, idList).set(MerchantOrder::getStatus, status).set(Objects.nonNull(reason), MerchantOrder::getReason, reason).set(MerchantOrder::getUpdateTime, LocalDateTime.now()).update();
        }
        return false;
    }

    @Override
    public boolean updateStatusById(Long id, Integer status, String reason) {
        return this.lambdaUpdate().eq(MerchantOrder::getId, id).set(MerchantOrder::getStatus, status).set(Objects.nonNull(reason), MerchantOrder::getReason, reason).set(MerchantOrder::getUpdateTime, LocalDateTime.now()).update();
    }

    @Override
    public long countByStatus(Long merchantId, int status) {
        return this.lambdaQuery().eq(Objects.nonNull(merchantId), MerchantOrder::getMerchantId, merchantId).eq(MerchantOrder::getStatus, status).count();
    }

    @Override
    public MerchantOrderDTO queryById(Long merchantOrderId) {
        MerchantOrder merchantOrder = this.lambdaQuery().eq(MerchantOrder::getId, merchantOrderId).one();
        MerchantOrderDTO merchantOrderDTO = BeanUtil.copyProperties(merchantOrder, MerchantOrderDTO.class);
        List<OrderItem> items = orderItemService.queryByMerchantOrderId(merchantOrderId);
        merchantOrderDTO.setItems(BeanUtil.copyToList(items, OrderItemDTO.class));
        return merchantOrderDTO;
    }

    @Override
    public List<MerchantOrder> queryByIdList(List<Long> merchantOrderidList) {
        if (CollUtil.isEmpty(merchantOrderidList)) {
            return Collections.emptyList();
        }
        return this.lambdaQuery().in(MerchantOrder::getId, merchantOrderidList).orderByDesc(MerchantOrder::getCreateTime).list();
    }

    @Override
    public boolean updateStatusByOrderNo(String merOrderNo, Integer status, String reason) {
        return this.lambdaUpdate().eq(MerchantOrder::getMerOrderNo, merOrderNo).set(MerchantOrder::getStatus, status).set(Objects.nonNull(reason), MerchantOrder::getReason, reason).set(MerchantOrder::getUpdateTime, LocalDateTime.now()).update();
    }

    @Override
    public MerchantOrderDTO queryByMerOrderNo(String merOrderNo) {
        MerchantOrder merchantOrder = this.lambdaQuery().eq(MerchantOrder::getMerOrderNo, merOrderNo).one();
        MerchantOrderDTO merchantOrderDTO = BeanUtil.copyProperties(merchantOrder, MerchantOrderDTO.class);
        List<OrderItem> items = orderItemService.queryByMerchantOrderId(merchantOrder.getId());
        merchantOrderDTO.setItems(BeanUtil.copyToList(items, OrderItemDTO.class));
        return merchantOrderDTO;
    }

    @Override
    public List<MerchantOrder> queryByNoList(List<String> merchantOrderNoList) {
        if (CollUtil.isEmpty(merchantOrderNoList)) {
            return Collections.emptyList();
        }
        return this.lambdaQuery().in(MerchantOrder::getMerOrderNo, merchantOrderNoList).list();
    }

    @Override
    public Pageable<MerchantOrderDTO> queryUserPage(UserQueryOrderPageBO userQueryOrderPageBO) {
        if (Objects.isNull(userQueryOrderPageBO.getUserId())) {
            throw BizException.build("user id  cannot be null");
        }

        Page<MerchantOrder> page = this.lambdaQuery().eq(MerchantOrder::getBuyerId, userQueryOrderPageBO.getUserId()).eq(Objects.nonNull(userQueryOrderPageBO.getStatus()), MerchantOrder::getStatus, userQueryOrderPageBO.getStatus()).orderByDesc(MerchantOrder::getCreateTime).page(new Page<>(userQueryOrderPageBO.getPageNum(), userQueryOrderPageBO.getPageSize()));
        return PageUtil.toPage(page, MerchantOrderDTO.class);
    }

    @Override
    public boolean migrateGuestOrder(Long guestId, Long userId) {
        return this.lambdaUpdate().eq(MerchantOrder::getBuyerId, guestId)
                .eq(MerchantOrder::getBuyerType, BuyerType.GUEST.getType())
                .set(MerchantOrder::getBuyerId, userId)
                .set(MerchantOrder::getBuyerType, BuyerType.LOVE_ACCOUNT.getType())
                .set(MerchantOrder::getUpdateTime, LocalDateTime.now()).update();
    }
}
