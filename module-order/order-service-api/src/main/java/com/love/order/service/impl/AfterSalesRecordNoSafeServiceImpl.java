package com.love.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.util.PageUtil;
import com.love.order.bo.*;
import com.love.order.dto.AfterSalesRecordDetailDTO;
import com.love.order.dto.AfterSalesSkuDTO;
import com.love.order.dto.OrderDTO;
import com.love.order.dto.OrderItemDTO;
import com.love.order.entity.AfterSalesRecord;
import com.love.order.entity.AfterSalesSku;
import com.love.order.enums.*;
import com.love.order.mapper.AfterSalesRecordMapper;
import com.love.order.service.AfterSalesRecordService;
import com.love.order.service.AfterSalesSkuService;
import com.love.order.service.OrderRefundService;
import com.love.order.service.OrderService;
import com.love.order.util.AfterSaleUtil;
import com.love.order.util.BigDecimalCollectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * (AfterSalesRecord)表服务实现类
 *
 * @author eric
 * @since 2023-07-11 16:46:16
 */
@Service("afterSalesRecordService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class AfterSalesRecordNoSafeServiceImpl extends ServiceImpl<AfterSalesRecordMapper, AfterSalesRecord> implements AfterSalesRecordService {

    private final OrderRefundService orderRefundService;
    private final AfterSalesSkuService afterSalesSkuService;

    @Autowired
    private OrderService orderService;

    @Override
    public boolean save(AfterSalesRecord entity) {
        return super.save(entity);
    }

    @Override
    public AfterSalesRecord queryByAfterSaleNo(String afterSaleNo) {
        return this.lambdaQuery().eq(AfterSalesRecord::getAfterSaleNo, afterSaleNo).one();
    }

    @Override
    public List<AfterSalesRecord> queryByOrderNo(String orderNo) {
        return this.lambdaQuery().eq(AfterSalesRecord::getOrderNo, orderNo).list();
    }

    @Override
    public List<AfterSalesSku> queryAfterSaleSkuList(String orderNo, String merOrderNo) {
        List<AfterSalesRecord> salesRecordEntityList = this.lambdaQuery()
                .eq(Objects.nonNull(orderNo), AfterSalesRecord::getOrderNo, orderNo)
                .eq(Objects.nonNull(merOrderNo), AfterSalesRecord::getMerOrderNo, merOrderNo)
                .ne(AfterSalesRecord::getAfterSaleStatus, AfterSaleStatus.CANCELLED.getStatus())
                .ne(AfterSalesRecord::getAfterSaleStatus, AfterSaleStatus.DECLINED.getStatus())
                .list();
        if (CollectionUtil.isEmpty(salesRecordEntityList)) {
            return Lists.newArrayList();
        }

        return afterSalesSkuService.queryAfterSaleSkuList(salesRecordEntityList.stream().map(AfterSalesRecord::getAfterSaleNo).collect(Collectors.toList()));
    }

    @Override
    public List<AfterSalesSku> queryAfterSaleSkuList(String orderNo, String merOrderNo, List<AfterSalesRecordQuerySkuBO> querySkuBOList) {
        List<AfterSalesSku> afterSaleSkuList = queryAfterSaleSkuList(orderNo, merOrderNo);
        return afterSaleSkuList.stream().filter(t1 -> querySkuBOList.stream().anyMatch(t2 -> t1.getGoodsId().equals(t2.getGoodsId()) && t1.getSkuId().equals(t2.getSkuId())))
                .collect(Collectors.toList());
    }

    @Override
    public Page<AfterSalesRecord> page(AfterSalesOrderQueryPageBO afterSalesOrderQueryPageBO) {
        LambdaQueryChainWrapper<AfterSalesRecord> queryChainWrapper = this.lambdaQuery()
                .eq(StringUtils.isNotBlank(afterSalesOrderQueryPageBO.getAfterSaleNo()), AfterSalesRecord::getAfterSaleNo, afterSalesOrderQueryPageBO.getAfterSaleNo())
                .eq(StringUtils.isNotBlank(afterSalesOrderQueryPageBO.getMerOrderNo()), AfterSalesRecord::getMerOrderNo, afterSalesOrderQueryPageBO.getMerOrderNo())
                .eq(Objects.nonNull(afterSalesOrderQueryPageBO.getMerchantId()), AfterSalesRecord::getMerchantId, afterSalesOrderQueryPageBO.getMerchantId())
                .eq(Objects.nonNull(afterSalesOrderQueryPageBO.getBuyerId()), AfterSalesRecord::getBuyerId, afterSalesOrderQueryPageBO.getBuyerId());

        LocalDateTime begin = afterSalesOrderQueryPageBO.getStartTime();
        LocalDateTime end = afterSalesOrderQueryPageBO.getEndTime();

        if (Objects.nonNull(begin) && Objects.nonNull(end) && end.isAfter(begin)) {
            queryChainWrapper.between(AfterSalesRecord::getCreateTime, begin, end);
        }

        queryChainWrapper.orderByDesc(AfterSalesRecord::getCreateTime);
        return queryChainWrapper.page(new Page<>(afterSalesOrderQueryPageBO.getPageNum(), afterSalesOrderQueryPageBO.getPageSize()));
    }

    @Override
    public AfterSalesRecord customerApply(AfterSalesApplyBO applyBO) {
        // 1.1 check: A product SKU can only be applied for once
        List<AfterSalesSku> afterSaleSkuList = this.queryAfterSaleSkuList(applyBO.getOrderNo(), applyBO.getMerOrderNo(), applyBO.getSkuBOList().stream().map(t -> new AfterSalesRecordQuerySkuBO(t.getGoodsId(), t.getSkuId())).collect(Collectors.toList()));
        if (CollectionUtil.isNotEmpty(afterSaleSkuList)) {
            throw new BizException(afterSaleSkuList.stream().map(AfterSalesSku::getGoodsTitle).collect(Collectors.joining()) + " : This product already has a record of being in or successful after-sales service");
        }

        OrderDTO orderDTO = orderService.queryByOrderNo(QuerySimpleOrderBO.builder().orderNo(applyBO.getOrderNo()).build());

        List<OrderItemDTO> orderItemDTOList = orderDTO.getMerchantOrders().stream().flatMap(t -> t.getItems().stream()).filter(t1 -> applyBO.getSkuBOList()
                .stream().anyMatch(t2 -> t1.getGoodsId().equals(t2.getGoodsId()) && t1.getSkuId().equals(t2.getSkuId()))).collect(Collectors.toList());
        if (orderItemDTOList.size() != applyBO.getSkuBOList().size()) {
            throw new BizException("order item is error");
        }
        Map<Long, Map<Long, OrderItemDTO>> orderItemMap = orderItemDTOList.stream().collect(Collectors.groupingBy(OrderItemDTO::getGoodsId, Collectors.toMap(OrderItemDTO::getSkuId, Function.identity())));

        // 1.2 check refund fee
        BigDecimal frontRefundAmount = applyBO.getSkuBOList().stream().collect(BigDecimalCollectors.summingBigDecimal(t -> t.getPrice().multiply(new BigDecimal(t.getQty()))));
        if (frontRefundAmount.compareTo(applyBO.getRefundAmount()) != 0) {
            throw new BizException("Front refund amount calculation error");
        }
        BigDecimal calRefundAmount = applyBO.getRefundAmount(); // Calculation of SKU price for specific orders
        if (calRefundAmount.compareTo(applyBO.getRefundAmount()) != 0) {
            throw new BizException("Refund amount calculation error");
        }

        // 2.create record and create sku
        String afterSaleNo = AfterSaleUtil.getAfterSaleNo();
        AfterSalesRecord afterSalesRecordEntity = new AfterSalesRecord()
                .setAfterSaleNo(afterSaleNo)
                .setBuyerId(applyBO.getBuyerId())
                .setMerchantId(applyBO.getMerchantId())
                .setOrderNo(applyBO.getOrderNo())
                .setMerOrderNo(applyBO.getMerOrderNo())
                .setAfterSaleType(applyBO.getAfterSaleType())
                .setSaleAmount(applyBO.getSaleAmount())
                .setShippingFee(BigDecimal.ZERO)
                .setPayAmount(applyBO.getSaleAmount().add(BigDecimal.ZERO))
                .setRefundAmount(applyBO.getRefundAmount())
                .setBrandId(applyBO.getBrandId())
                .setAfterSaleReason(applyBO.getAfterSaleReason())
                .setAfterSaleStatus(AfterSaleStatus.REQUESTED.getStatus())
                .setRefundStatus(AfterSaleRefundStatus.REFUND_DEFAULT.getType())
                .setAfterSaleState(AfterSaleState.TO_MERCHANT_DEAL_REFUND.getCode())
                .setConsumerDealStatus(AfterSaleDealStatus.PREPARE_PROCESS.getCode())
                .setConsumerDealResult(AfterSaleConsumeDealResult.NOT_DEAL.getCode())
                .setMerchantDealStatus(AfterSaleDealStatus.PREPARE_PROCESS.getCode())
                .setMerchantDealResult(AfterSaleDealResult.TO_DEAL.getCode())
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                ;
        boolean saveFlag = super.save(afterSalesRecordEntity);
        if (saveFlag) {
            List<AfterSalesSku> skuEntityList = Lists.newArrayList();
            applyBO.getSkuBOList().forEach(skuBO -> {
                OrderItemDTO orderItemDTO = orderItemMap.get(skuBO.getGoodsId()).get(skuBO.getSkuId());
                if(!orderItemDTO.getPrice().equals(skuBO.getPrice())){
                    throw new BizException("check price error");
                }
                if(orderItemDTO.getQty() < skuBO.getQty()){
                    throw new BizException("check num error");
                }
                skuEntityList.add(new AfterSalesSku()
                        .setMerchantId(applyBO.getMerchantId())
                        .setOrderNo(applyBO.getOrderNo())
                        .setBuyerId(applyBO.getBuyerId())
                        .setAfterSaleNo(afterSaleNo)
                        .setGoodsId(skuBO.getGoodsId())
                        .setSkuId(skuBO.getSkuId())
                        .setGoodsTitle(orderItemDTO.getGoodsTitle())
                        .setPrice(skuBO.getPrice())
                        .setQty(skuBO.getQty())
                        .setRefundAmount(skuBO.getPrice().multiply(new BigDecimal(skuBO.getQty())))
                        .setSkuImg(skuBO.getSkuImg())
                        .setSkuInfo(skuBO.getSkuInfo())
                        .setCreateTime(LocalDateTime.now())
                        .setUpdateTime(LocalDateTime.now())
                        .setMerOrderNo(applyBO.getMerOrderNo())
                );
            });
            boolean skuBatchFlag = afterSalesSkuService.saveSkuBatch(skuEntityList);
            if (!skuBatchFlag) {
                throw new BizException("system error, Please retry.");
            }
        }

        // do others
        return afterSalesRecordEntity;
    }

    @Override
    public AfterSalesRecord customerCancel(AfterSalesRecord entity) {
        // cancel
        entity.setAfterSaleStatus(AfterSaleStatus.CANCELLED.getStatus())
                .setRefundStatus(AfterSaleRefundStatus.REFUND_CLOSE.getType())
                .setAfterSaleState(AfterSaleState.CANCEL.getCode())
                .setConsumerDealStatus(AfterSaleDealStatus.PROCESSED.getCode())
                .setConsumerDealResult(AfterSaleConsumeDealResult.CANCEL.getCode())
                .setConsumerDealTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
        ;
        super.saveOrUpdate(entity);
        // update order goods sku info

        return entity;
    }

    @Override
    public AfterSalesRecord merchantAgree(AfterSalesRecord entity) {
        //check
        //update merchant deal status
        entity.setMerchantDealStatus(AfterSaleDealStatus.PROCESSED.getCode())
                .setMerchantDealResult(AfterSaleDealResult.APPROVE.getCode())
                .setMerchantDealTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setRefundNo(AfterSaleUtil.getRefundNo())
                .setAfterSaleStatus(AfterSaleStatus.REFUNDING.getStatus())
                .setAfterSaleState(AfterSaleState.TO_MERCHANT_DEAL_REFUND_RESULT.getCode())
                .setUpdateTime(LocalDateTime.now())
        ;
        super.saveOrUpdate(entity);
        return entity;
    }

    private List<AfterSalesSku> queryAfterSaleSkuSuccessList(String orderNo) {
        List<AfterSalesRecord> salesRecordEntityList = this.lambdaQuery().eq(AfterSalesRecord::getOrderNo, orderNo).eq(AfterSalesRecord::getAfterSaleStatus, AfterSaleStatus.REFUNDED.getStatus())
                .list();
        if (CollectionUtil.isEmpty(salesRecordEntityList)) {
            return Lists.newArrayList();
        }

        return afterSalesSkuService.queryAfterSaleSkuList(salesRecordEntityList.stream().map(AfterSalesRecord::getAfterSaleNo).collect(Collectors.toList()));
    }
    @Override
    public AfterSalesRecord merchantAgreeRefundCallBack(AfterSalesRecord entity, RefundStatus refundStatusEnum, String thirdRefundNo) {
        //refund
        OrderRefundBO refundBO = new OrderRefundBO().setRefundNo(entity.getRefundNo())
                .setAfterSaleNo(entity.getAfterSaleNo())
                .setThirdRefundNo(thirdRefundNo)
                .setBuyerId(entity.getBuyerId())
                .setOrderNo(entity.getOrderNo())
                .setMerOrderNo(entity.getMerOrderNo())
                .setMerchantId(entity.getMerchantId())
                .setRefundAmount(entity.getRefundAmount());
        orderRefundService.refund(refundBO);

        if (RefundStatus.REFUND_SUCCESS.equals(refundStatusEnum)) {
            entity.setAfterSaleStatus(AfterSaleStatus.REFUNDED.getStatus())
                    .setRefundStatus(AfterSaleRefundStatus.REFUND_SUCCESS.getType())
                    .setAfterSaleState(AfterSaleState.REFUND_SUCCESS.getCode())
                    .setUpdateTime(LocalDateTime.now())
            ;

        } else if (RefundStatus.REFUND_FAIL.equals(refundStatusEnum)) {
            entity.setAfterSaleStatus(AfterSaleStatus.REFUND_FAIL.getStatus())
                    .setRefundStatus(AfterSaleRefundStatus.REFUND_FAIL.getType())
                    .setAfterSaleState(AfterSaleState.REFUND_FAIL.getCode())
                    .setUpdateTime(LocalDateTime.now())
            ;
        }


        super.saveOrUpdate(entity);

        if (RefundStatus.REFUND_SUCCESS.equals(refundStatusEnum)) {
            //order all refund , close order
            orderRefundSuccess(entity);
        }

        return entity;
    }

    private void orderRefundSuccess(AfterSalesRecord entity) {
        List<AfterSalesSku> refundItemList = this.queryAfterSaleSkuSuccessList(entity.getOrderNo());
        OrderDTO orderDTO = orderService.queryByOrderNo(QuerySimpleOrderBO.builder().orderNo(entity.getOrderNo()).build());
        List<OrderItemDTO> orderItemList = orderDTO.getMerchantOrders().stream().flatMap(t -> t.getItems().stream()).collect(Collectors.toList());

        List<AfterSalesSku> merRefundItemList = refundItemList.stream().filter(t -> t.getMerOrderNo().equals(entity.getMerOrderNo())).collect(Collectors.toList());
        List<OrderItemDTO> merOrderItemList = orderDTO.getMerchantOrders().stream().filter(t -> t.getMerOrderNo().equals(entity.getMerOrderNo())).flatMap(t -> t.getItems().stream()).collect(Collectors.toList());
        if(refundItemList.size() == orderItemList.size()){
            OrderUpdateByOrderNoBO orderUpdateByOrderNoBO = new OrderUpdateByOrderNoBO();
            orderUpdateByOrderNoBO.setCascade(true);
            orderUpdateByOrderNoBO.setStatus(OrderStatus.CLOSED.getStatus());
            orderUpdateByOrderNoBO.setOrderNo(entity.getOrderNo());
            orderUpdateByOrderNoBO.setReason("refunded");

            orderService.updateByOrderNo(orderUpdateByOrderNoBO);
        }else if(merRefundItemList.size() == merOrderItemList.size()){
            OrderUpdateByMerOrderNoBO orderUpdateByMerOrderNoBO = new OrderUpdateByMerOrderNoBO();
            orderUpdateByMerOrderNoBO.setCascade(true);
            orderUpdateByMerOrderNoBO.setStatus(OrderStatus.CLOSED.getStatus());
            orderUpdateByMerOrderNoBO.setMerOrderNo(entity.getMerOrderNo());
            orderUpdateByMerOrderNoBO.setSkuIdList(merRefundItemList.stream().map(AfterSalesSku::getSkuId).collect(Collectors.toList()));
            orderUpdateByMerOrderNoBO.setReason("refunded");

            orderService.updateByMerOrderNo(orderUpdateByMerOrderNoBO);
        }else {
            OrderItemUpdateByMerOrderNoBO orderItemUpdateByMerOrderNoBO = new OrderItemUpdateByMerOrderNoBO();
            orderItemUpdateByMerOrderNoBO.setMerOrderNo(entity.getMerOrderNo());
            orderItemUpdateByMerOrderNoBO.setStatus(OrderStatus.CLOSED.getStatus());
            orderItemUpdateByMerOrderNoBO.setSkuIdList(merRefundItemList.stream().map(AfterSalesSku::getSkuId).collect(Collectors.toList()));

            orderService.updateOrderItemByMerOrderNo(orderItemUpdateByMerOrderNoBO);
        }
    }

    @Override
    public AfterSalesRecord merchantReject(AfterSalesRecord entity) {
        entity.setAfterSaleStatus(AfterSaleStatus.DECLINED.getStatus())
                .setRefundStatus(AfterSaleRefundStatus.REFUND_CLOSE.getType())
                .setAfterSaleState(AfterSaleState.CANCEL.getCode())
                .setMerchantDealStatus(AfterSaleDealStatus.PREPARE_PROCESS.getCode())
                .setMerchantDealResult(AfterSaleDealResult.TO_DEAL.getCode())
                .setMerchantDealTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
        ;

        super.saveOrUpdate(entity);
        return entity;
    }

    @Override
    public Pageable<AfterSalesRecordDetailDTO> afterSalesPage(AfterSalesOrderQueryPageBO afterSalesOrderQueryPageBO) {
        Pageable<AfterSalesRecordDetailDTO> pageable = PageUtil.toPage(this.page(afterSalesOrderQueryPageBO), AfterSalesRecordDetailDTO.class);
        if (CollUtil.isNotEmpty(pageable.getRecords())) {
            Map<String, List<AfterSalesSku>> afterSaleSkuMap = afterSalesSkuService.queryAfterSaleSkuList(pageable.getRecords().stream().map(AfterSalesRecordDetailDTO::getAfterSaleNo).collect(Collectors.toList())).stream().collect(Collectors.groupingBy(k -> k.getAfterSaleNo()));
            pageable.getRecords().stream().forEach(r -> {
                List<AfterSalesSku> afterSalesSkuEntityList = afterSaleSkuMap.get(r.getAfterSaleNo());
                if (CollUtil.isEmpty(afterSalesSkuEntityList)) {
                    log.info("refund item is null, after_sale_no:" + r.getAfterSaleNo());
                } else {
                    r.setItems(BeanUtil.copyToList(afterSalesSkuEntityList, AfterSalesSkuDTO.class));
                }
            });
        }
        return pageable;
    }

    @Override
    public AfterSalesRecordDetailDTO afterSalesDetail(AfterSalesQueryBO afterSalesOrderQueryPageParam) {
        AfterSalesRecord afterSalesRecordEntity = this.queryByAfterSaleNo(afterSalesOrderQueryPageParam.getAfterSaleNo());
        if (afterSalesRecordEntity == null) {
            throw BizException.build("after sale order does not exist");
        }

        AfterSalesRecordDetailDTO afterSalesRecordDetailDTO = BeanUtil.copyProperties(afterSalesRecordEntity, AfterSalesRecordDetailDTO.class);
        afterSalesRecordDetailDTO.setItems(afterSalesSkuService.queryAfterSaleSkuList(Lists.newArrayList(afterSalesRecordEntity.getAfterSaleNo())).stream().map(s -> BeanUtil.copyProperties(s, AfterSalesSkuDTO.class)).collect(Collectors.toList()));
        return afterSalesRecordDetailDTO;
    }

    @Override
    public List<AfterSalesRecordDetailDTO> queryAfterSaleRecordList(String merOrderNo) {
        List<AfterSalesRecord> salesRecordEntityList = this.lambdaQuery()
                .eq(Objects.nonNull(merOrderNo), AfterSalesRecord::getMerOrderNo, merOrderNo)
                .ne(AfterSalesRecord::getAfterSaleStatus, AfterSaleStatus.CANCELLED.getStatus())
                .list();
        if (CollectionUtil.isEmpty(salesRecordEntityList)) {
            return Lists.newArrayList();
        }

        List<AfterSalesRecordDetailDTO> recordDetailDTOList = BeanUtil.copyToList(salesRecordEntityList, AfterSalesRecordDetailDTO.class);
        Map<String, List<AfterSalesSku>> map = afterSalesSkuService.queryAfterSaleSkuList(salesRecordEntityList.stream().map(AfterSalesRecord::getAfterSaleNo).collect(Collectors.toList())).stream().collect(Collectors.groupingBy(AfterSalesSku::getAfterSaleNo));
        recordDetailDTOList.forEach(r -> r.setItems(BeanUtil.copyToList(map.get(r.getAfterSaleNo()), AfterSalesSkuDTO.class)));

        return recordDetailDTOList;
    }
}