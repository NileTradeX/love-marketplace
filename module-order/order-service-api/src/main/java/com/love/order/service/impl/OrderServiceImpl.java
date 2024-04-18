package com.love.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PostProcessor;
import com.love.order.bo.*;
import com.love.order.dto.*;
import com.love.order.entity.MerchantOrder;
import com.love.order.entity.Order;
import com.love.order.entity.OrderItem;
import com.love.order.enums.AfterSaleStatus;
import com.love.order.enums.BuyerType;
import com.love.order.enums.OrderStatus;
import com.love.order.mapper.OrderMapper;
import com.love.order.query.CountByUserIdAndGoodsIdQuery;
import com.love.order.service.AfterSalesRecordService;
import com.love.order.service.MerchantOrderService;
import com.love.order.service.OrderItemService;
import com.love.order.service.OrderService;
import com.love.order.util.OrderNoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService, PostProcessor<Order, OrderDTO> {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private MerchantOrderService merchantOrderService;
    @Autowired
    private AfterSalesRecordService afterSalesRecordService;


    @Override
    public void process(Order src, OrderDTO dst) {
        List<MerchantOrder> merchantOrders = merchantOrderService.queryByOrderId(src.getId());
        if (CollUtil.isNotEmpty(merchantOrders)) {
            List<MerchantOrderDTO> merchantOrderDTOS = new ArrayList<>();
            for (MerchantOrder merchantOrder : merchantOrders) {
                MerchantOrderDTO merchantOrderDTO = BeanUtil.copyProperties(merchantOrder, MerchantOrderDTO.class);
                merchantOrderDTOS.add(merchantOrderDTO);
                List<OrderItem> items = orderItemService.queryByMerchantOrderId(merchantOrder.getId());
                merchantOrderDTO.setItems(BeanUtil.copyToList(items, OrderItemDTO.class));
            }
            dst.setMerchantOrders(merchantOrderDTOS);
            return;
        }
        throw BizException.build("this order can not find related merchant orders");
    }

    @Override
    public Pageable<MerchantOrderDTO> page(OrderQueryPageBO orderQueryPageBO) {
        Integer timeRange = orderQueryPageBO.getTimeRange();
        if (Objects.nonNull(timeRange)) {
            if (1 == timeRange) {
                orderQueryPageBO.setBeginTime(LocalDateTime.now().minusMonths(6));
                orderQueryPageBO.setEndTime(null);
            } else if (2 == timeRange) {
                orderQueryPageBO.setBeginTime(null);
                orderQueryPageBO.setEndTime(LocalDateTime.now().minusMonths(6));
            }
        }

        List<Long> merchantOrderIdList = this.baseMapper.queryPage(orderQueryPageBO);
        List<MerchantOrder> merchantOrders = merchantOrderService.queryByIdList(merchantOrderIdList);
        Pageable<MerchantOrderDTO> pageable = new Pageable<>();
        pageable.setTotal(Optional.of(this.baseMapper.countForPage(orderQueryPageBO)).orElse(0L));
        pageable.setPageNum(orderQueryPageBO.getPageNum());
        pageable.setPageSize(orderQueryPageBO.getPageSize());
        pageable.setRecords(merchantOrders.stream().map(merchantOrder -> {
            MerchantOrderDTO merchantOrderDTO = BeanUtil.copyProperties(merchantOrder, MerchantOrderDTO.class);
            merchantOrderDTO.setItems(BeanUtil.copyToList(orderItemService.queryByMerchantOrderId(merchantOrder.getId()), OrderItemDTO.class));
            return merchantOrderDTO;
        }).collect(Collectors.toList()));
        return pageable;
    }

    @Override
    public OrderDTO queryById(IdParam idParam) {
        Order order = this.getById(idParam.getId());
        if (Objects.isNull(order)) {
            throw BizException.build("order not exist");
        }
        OrderDTO orderDTO = BeanUtil.copyProperties(order, OrderDTO.class);
        this.process(order, orderDTO);
        return orderDTO;
    }

    @Override
    public OrderDTO queryByOrderNo(QuerySimpleOrderBO queryByOrderNoBO) {
        Order order = this.lambdaQuery().eq(Order::getOrderNo, queryByOrderNoBO.getOrderNo()).one();
        if (Objects.isNull(order)) {
            throw BizException.build("order not exist");
        }
        OrderDTO orderDTO = BeanUtil.copyProperties(order, OrderDTO.class);
        this.process(order, orderDTO);
        return orderDTO;
    }

    @Override
    public OrderSimpleStatDTO simpleStat(OrderSimpleStatBO orderSimpleStatBO) {
        OrderSimpleStatDTO orderSimpleStatDTO = new OrderSimpleStatDTO();
        orderSimpleStatDTO.setPendingShipment(merchantOrderService.countByStatus(orderSimpleStatBO.getMerchantId(), OrderStatus.PENDING_SHIPMENT.getStatus()));
        orderSimpleStatDTO.setPendingReceipt(merchantOrderService.countByStatus(orderSimpleStatBO.getMerchantId(), OrderStatus.PENDING_RECEIPT.getStatus()));
        return orderSimpleStatDTO;
    }

    @Override
    public UserOrderStatDTO userStat(UserOrderStatBO userOrderStatBO) {
        UserOrderStatDTO userOrderStatDTO = new UserOrderStatDTO();
        userOrderStatDTO.setOrders(Math.toIntExact(this.lambdaQuery().eq(Order::getBuyerId, userOrderStatBO.getUserId()).eq(Order::getStatus, OrderStatus.COMPLETED.getStatus()).count()));
        userOrderStatDTO.setAmountSpent(this.getObj(Wrappers.<Order>query().select("sum(total_amount)").eq("buyer_id", userOrderStatBO.getUserId()).eq("status", OrderStatus.COMPLETED.getStatus()), x -> new BigDecimal(x.toString())));
        return userOrderStatDTO;
    }


    @Override
    public Pageable<MerchantOrderDTO> userPage(UserQueryOrderPageBO userQueryOrderPageBO) {
        if (Objects.isNull(userQueryOrderPageBO.getUserId())) {
            throw BizException.build("user id  cannot be null");
        }

        Pageable<MerchantOrderDTO> pageable = merchantOrderService.queryUserPage(userQueryOrderPageBO);
        for (MerchantOrderDTO record : pageable.getRecords()) {
            List<OrderItem> items = orderItemService.queryByMerchantOrderId(record.getId());
            record.setItems(BeanUtil.copyToList(items, OrderItemDTO.class));
        }
        return pageable;
    }

    @Override
    public Long countByUserId(IdParam idParam) {
        return this.lambdaQuery().eq(Order::getBuyerId, idParam.getId()).eq(Order::getStatus, OrderStatus.COMPLETED.getStatus()).count();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveTracking(OrderTrackingInfoBO orderTrackingInfoBO) {
        Long merchantOrderId = orderTrackingInfoBO.getMerchantOrderId();
        MerchantOrderDTO merchantOrder = merchantOrderService.queryById(merchantOrderId);
        this.lambdaUpdate().set(Order::getStatus, OrderStatus.PENDING_RECEIPT.getStatus()).set(Order::getUpdateTime, LocalDateTime.now()).eq(Order::getId, merchantOrder.getOrderId()).update();
        this.merchantOrderService.updateStatusById(merchantOrderId, OrderStatus.PENDING_RECEIPT.getStatus(), null);

        List<Long> excludeSkuIdList = new ArrayList<>();
        List<AfterSalesRecordDetailDTO> afterSaleRecordList = this.afterSalesRecordService.queryAfterSaleRecordList(merchantOrder.getMerOrderNo());
        for (AfterSalesRecordDetailDTO afterSalesRecordDetailDTO : afterSaleRecordList) {
            int status = afterSalesRecordDetailDTO.getAfterSaleStatus();
            if (status <= AfterSaleStatus.REFUNDED.getStatus() || status == AfterSaleStatus.REFUND_FAIL.getStatus()) {
                excludeSkuIdList.addAll(afterSalesRecordDetailDTO.getItems().stream().map(AfterSalesSkuDTO::getSkuId).collect(Collectors.toList()));
            }
        }

        return orderItemService.saveTracking(orderTrackingInfoBO, excludeSkuIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateTracking(OrderTrackingInfoBO orderTrackingInfoBO) {
        Long merchantOrderId = orderTrackingInfoBO.getMerchantOrderId();
        MerchantOrderDTO merchantOrder = merchantOrderService.queryById(merchantOrderId);
        if (merchantOrder.getStatus() == OrderStatus.PENDING_RECEIPT.getStatus()) {
            List<Long> excludeSkuIdList = new ArrayList<>();
            List<AfterSalesRecordDetailDTO> afterSaleRecordList = this.afterSalesRecordService.queryAfterSaleRecordList(merchantOrder.getMerOrderNo());
            for (AfterSalesRecordDetailDTO afterSalesRecordDetailDTO : afterSaleRecordList) {
                int status = afterSalesRecordDetailDTO.getAfterSaleStatus();
                if (status <= AfterSaleStatus.REFUNDED.getStatus() || status == AfterSaleStatus.REFUND_FAIL.getStatus()) {
                    excludeSkuIdList.addAll(afterSalesRecordDetailDTO.getItems().stream().map(AfterSalesSkuDTO::getSkuId).collect(Collectors.toList()));
                }
            }

            return orderItemService.updateTracking(orderTrackingInfoBO, excludeSkuIdList);
        } else {
            throw BizException.build("order is not in pending receipt status");
        }

    }

    @Override
    public OrderTrackInfoDTO queryTracking(IdParam idParam) {
        OrderItem orderItem = orderItemService.queryTracking(idParam.getId());
        if (Objects.isNull(orderItem)) {
            throw BizException.build("order does not exist");
        }
        OrderTrackInfoDTO orderTrackInfoDTO = BeanUtil.copyProperties(orderItem, OrderTrackInfoDTO.class);
        orderTrackInfoDTO.setId(idParam.getId());
        return orderTrackInfoDTO;
    }

    @Override
    public OrderDTO merchantOrderDetail(OrderQueryDetailBO orderQueryDetailBO) {
        MerchantOrderDTO merchantOrderDTO = merchantOrderService.queryById(orderQueryDetailBO.getMerchantOrderId());
        Order order = this.lambdaQuery().eq(Order::getId, merchantOrderDTO.getOrderId()).one();
        OrderDTO orderDTO = BeanUtil.copyProperties(order, OrderDTO.class);
        orderDTO.setMerchantOrders(Collections.singletonList(merchantOrderDTO));
        return orderDTO;
    }

    @Override
    public OrderSimpleDTO simple(QuerySimpleOrderBO querySimpleOrderBO) {
        Order order = this.lambdaQuery().select(Order::getId, Order::getBuyerId, Order::getStatus, Order::getBuyerType, Order::getTotalAmount, Order::getCreateTime).eq(Objects.nonNull(querySimpleOrderBO.getOrderNo()), Order::getOrderNo, querySimpleOrderBO.getOrderNo()).eq(Objects.nonNull(querySimpleOrderBO.getId()), Order::getId, querySimpleOrderBO.getId()).one();
        if (Objects.isNull(order)) {
            throw BizException.build("order does not exist");
        }
        return BeanUtil.copyProperties(order, OrderSimpleDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByOrderNo(OrderUpdateByOrderNoBO orderUpdateByOrderNoBO) {
        boolean result = this.lambdaUpdate().eq(Order::getOrderNo, orderUpdateByOrderNoBO.getOrderNo())
                .set(Order::getStatus, orderUpdateByOrderNoBO.getStatus())
                .set(Order::getUpdateTime, LocalDateTime.now())
                .update();

        if (result) {
            if (orderUpdateByOrderNoBO.isCascade()) {
                Order order = this.lambdaQuery().select(Order::getId).eq(Order::getOrderNo, orderUpdateByOrderNoBO.getOrderNo()).one();
                result = merchantOrderService.updateStatusByOrderId(order.getId(), orderUpdateByOrderNoBO.getStatus(), orderUpdateByOrderNoBO.getSkuIdList(), orderUpdateByOrderNoBO.getReason());
                if (result) {
                    result = orderItemService.updateStatusByOrderId(order.getId(), orderUpdateByOrderNoBO.getStatus(), orderUpdateByOrderNoBO.getSkuIdList());
                    if (!result) {
                        throw BizException.build("update order item status error");
                    }
                } else throw BizException.build("update merchant order status error");
            }
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByMerOrderNo(OrderUpdateByMerOrderNoBO orderUpdateByMerOrderNoBO) {
        boolean result = merchantOrderService.updateStatusByOrderNo(orderUpdateByMerOrderNoBO.getMerOrderNo(), orderUpdateByMerOrderNoBO.getStatus(), orderUpdateByMerOrderNoBO.getReason());
        if (result && orderUpdateByMerOrderNoBO.isCascade()) {
            MerchantOrderDTO merchantOrderDTO = merchantOrderService.queryByMerOrderNo(orderUpdateByMerOrderNoBO.getMerOrderNo());
            return this.orderItemService.updateStatusByMerOrderId(merchantOrderDTO.getId(), orderUpdateByMerOrderNoBO.getStatus(), orderUpdateByMerOrderNoBO.getSkuIdList());
        }
        return result;
    }

    @Override
    public Boolean updateOrderItemByMerOrderNo(OrderItemUpdateByMerOrderNoBO orderItemUpdateByMerOrderNoBO) {
        MerchantOrderDTO merchantOrderDTO = merchantOrderService.queryByMerOrderNo(orderItemUpdateByMerOrderNoBO.getMerOrderNo());
        return this.orderItemService.updateStatusByMerOrderId(merchantOrderDTO.getId(), orderItemUpdateByMerOrderNoBO.getStatus(), orderItemUpdateByMerOrderNoBO.getSkuIdList());
    }

    @Override
    public OrderDTO queryByOrderNoAndEmail(QueryByEmailAndOrderNoBO queryByOrderNoBO) {
        MerchantOrderDTO merchantOrderDTO = merchantOrderService.queryByMerOrderNo(queryByOrderNoBO.getMerOrderNo());
        if (Objects.isNull(merchantOrderDTO)) {
            throw BizException.build("order does not exist");
        }
        Order order = this.lambdaQuery().eq(Order::getId, merchantOrderDTO.getOrderId()).one();
        if ((BuyerType.GUEST.getType() == order.getBuyerType() && Objects.equals(order.getConsigneeEmail(), queryByOrderNoBO.getEmail()))
                || (BuyerType.LOVE_ACCOUNT.getType() == order.getBuyerType() && Objects.equals(order.getBuyerId(), queryByOrderNoBO.getUserId()))) {
            OrderDTO orderDTO = BeanUtil.copyProperties(order, OrderDTO.class);
            orderDTO.setMerchantOrders(Collections.singletonList(merchantOrderDTO));
            return orderDTO;
        } else {
            throw BizException.build("order does not exist");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MultiItemOrderDTO create(MultiItemOrderSaveBO multiItemOrderSaveBO) {
        String orderNo = multiItemOrderSaveBO.getOrderNo();
        if (StringUtils.isBlank(orderNo)) {
            throw BizException.build("orderNo cannot be null");
        }

        List<MultiItemOrderSaveBO.Brand> brands = multiItemOrderSaveBO.getBrands();
        if (CollUtil.isEmpty(brands)) {
            throw BizException.build("order item can't be empty !");
        }

        Order order = BeanUtil.copyProperties(multiItemOrderSaveBO, Order.class, "brands");
        order.setStatus(OrderStatus.AWAIT_PAYMENT.getStatus());
        this.save(order);

        AtomicInteger indexer = new AtomicInteger(1);

        MerchantOrder merchantOrder;
        for (MultiItemOrderSaveBO.Brand brand : brands) {
            merchantOrder = new MerchantOrder();
            merchantOrder.setMerOrderNo("M" + OrderNoUtil.nextId());
            merchantOrder.setMerchantId(brand.getMerchantId());
            merchantOrder.setStatus(OrderStatus.AWAIT_PAYMENT.getStatus());
            merchantOrder.setOrderId(order.getId());
            merchantOrder.setBrandId(brand.getBrandId());
            merchantOrder.setOrderNo(orderNo);
            merchantOrder.setBuyerId(order.getBuyerId());
            merchantOrder.setBuyerType(order.getBuyerType());

            List<OrderItemSaveBO> items = brand.getItems();
            merchantOrder.setTotalAmount(items.stream().map(i -> i.getPrice().multiply(new BigDecimal(i.getQty()))).collect(Collectors.toList()).stream().reduce(BigDecimal.ZERO, BigDecimal::add));
            merchantOrderService.save(merchantOrder);

            OrderItem orderItem;
            List<OrderItem> orderItems = new ArrayList<>();
            for (OrderItemSaveBO item : items) {
                orderItem = BeanUtil.copyProperties(item, OrderItem.class);
                orderItem.setMerchantId(merchantOrder.getMerchantId());
                orderItem.setMerchantOrderId(merchantOrder.getId());
                orderItem.setOrderId(order.getId());
                orderItem.setOrderItemNo(orderNo + "-" + indexer.getAndAdd(1));
                orderItem.setStatus(OrderStatus.AWAIT_PAYMENT.getStatus());
                orderItems.add(orderItem);
            }
            orderItemService.saveBatch(orderItems);
        }

        return MultiItemOrderDTO.builder().orderId(order.getId()).orderNo(orderNo).build();
    }

    @Override
    public List<OrderSimpleDTO> queryOrderList(QueryOrderNoListBO queryOrderNoListBO) {
        if (CollUtil.isEmpty(queryOrderNoListBO.getOrderNoList())) {
            return Lists.newArrayList();
        }

        List<Order> orderList = this.lambdaQuery().in(Order::getOrderNo, queryOrderNoListBO.getOrderNoList()).list();
        return BeanUtil.copyToList(orderList, OrderSimpleDTO.class);
    }

    public Boolean checkOrderCompleted(IdParam idParam) {
        return this.orderItemService.checkOrderCompleted(idParam.getId());
    }

    @Override
    public List<MerchantOrderSimpleDTO> queryMerchantOrderList(QueryMerchantOrderNoListBO queryMerchantOrderNoListBO) {
        if (CollUtil.isEmpty(queryMerchantOrderNoListBO.getMerchantOrderNoList())) {
            return Lists.newArrayList();
        }

        return BeanUtil.copyToList(merchantOrderService.queryByNoList(queryMerchantOrderNoListBO.getMerchantOrderNoList()), MerchantOrderSimpleDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean migrateGuestOrder(MigrateGuestOrderBO migrateGuestOrderBO) {
        Long guestId = migrateGuestOrderBO.getGuestId();
        Long userId = migrateGuestOrderBO.getUserId();
        String buyerName = migrateGuestOrderBO.getBuyerName();

        if (Objects.isNull(guestId) || Objects.isNull(userId) || StrUtil.isEmpty(buyerName)) {
            throw BizException.build("param cannot be empty");
        }
        this.lambdaUpdate().eq(Order::getBuyerId, guestId)
                .eq(Order::getBuyerType, BuyerType.GUEST.getType())
                .set(Order::getBuyerId, userId)
                .set(Order::getBuyerName, buyerName)
                .set(Order::getBuyerType, BuyerType.LOVE_ACCOUNT.getType())
                .set(Order::getUpdateTime, LocalDateTime.now())
                .update();

        merchantOrderService.migrateGuestOrder(guestId, userId);

        return Boolean.TRUE;
    }

    @Override
    public OrderItemDTO queryByOrderItemId(IdParam idParam) {
        OrderItem orderItem = orderItemService.queryById(idParam.getId());
        if (Objects.nonNull(orderItem)) {
            return BeanUtil.copyProperties(orderItem, OrderItemDTO.class);
        }
        return null;
    }

    @Override
    public List<Order> queryByStatus(int status) {
        return this.lambdaQuery().eq(Order::getStatus, status).list();
    }

    @Override
    public Boolean checkPromoEligibility(PromoEligibilityParam param) {
        CountByUserIdAndGoodsIdQuery historyQuery = CountByUserIdAndGoodsIdQuery.builder()
                .userId(param.getUserId())
                .amount(param.getAmount())
                .beginTime(param.getBeginTime())
                .endTime(param.getEndTime())
                .build();
        long hisCnt = this.baseMapper.countByUserIdAndGoodsId(historyQuery);
        if(hisCnt==0)return Boolean.FALSE;

        CountByUserIdAndGoodsIdQuery giftQuery = CountByUserIdAndGoodsIdQuery.builder()
                .userId(param.getUserId())
                .goodsId(param.getGoodsId())
                .build();
        long giftCnt = this.baseMapper.countByUserIdAndGoodsId(giftQuery);
        return 0==giftCnt;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean completeOrderByTrack(CompleteOrderByTrackBO completeOrderByTrackBO) {
        Boolean result=Boolean.FALSE;
        List<OrderItem> orderItems = orderItemService.queryByTrack(completeOrderByTrackBO.getTrackingNo(), completeOrderByTrackBO.getCarriers());
        //Cascade update order status
        //item->merchant->order
        if (CollectionUtil.isNotEmpty(orderItems)) {
            Long orderId = orderItems.get(0).getOrderId();
            Long merchantOrderId = orderItems.get(0).getMerchantOrderId();
            Integer status = orderItems.get(0).getStatus();
            if (OrderStatus.COMPLETED.getStatus() != status &&
                    OrderStatus.CLOSED.getStatus() != status) {
                log.info("Completed order orderId={} completeOrderByTrackBO={}", orderId, completeOrderByTrackBO);
                orderItemService.updateStatusByMerOrderId(merchantOrderId, OrderStatus.COMPLETED.getStatus(), null);
                result=true;

                merchantOrderService.updateStatusById(merchantOrderId, OrderStatus.COMPLETED.getStatus(), null);
                List<MerchantOrder> merchantOrders = merchantOrderService.queryByOrderId(orderId);
                long incompleteCnt = merchantOrders.stream().filter(e -> OrderStatus.COMPLETED.getStatus() != e.getStatus().intValue()).count();
                if (0 == incompleteCnt) {
                    this.lambdaUpdate().eq(Order::getId, orderId)
                            .set(Order::getStatus, OrderStatus.COMPLETED.getStatus())
                            .set(Order::getUpdateTime, LocalDateTime.now())
                            .update();
                }
            }
        }
        return result;
    }

    @Override
    public List<OrderItemDTO> queryByTrack(CompleteOrderByTrackBO completeOrderByTrackBO) {
        List<OrderItem> orderItems = orderItemService.queryByTrack(completeOrderByTrackBO.getTrackingNo(), completeOrderByTrackBO.getCarriers());
        return BeanUtil.copyToList(orderItems,OrderItemDTO.class);
    }
}
