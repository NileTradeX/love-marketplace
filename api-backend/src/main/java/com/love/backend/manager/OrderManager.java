package com.love.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.love.backend.model.param.AfterSalesOrderQueryPageParam;
import com.love.backend.model.param.AfterSalesQueryParam;
import com.love.backend.model.param.OrderQueryPageParam;
import com.love.backend.model.param.OrderSimpleStatParam;
import com.love.backend.model.vo.*;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.MathUtil;
import com.love.common.util.ObjectUtil;
import com.love.common.util.PageableUtil;
import com.love.common.util.PostProcessor;
import com.love.goods.bo.UpdateGoodsSalesVolumeBO;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.client.GoodsSkuFeignClient;
import com.love.goods.dto.GoodsSimpleDTO;
import com.love.goods.dto.GoodsSkuDTO;
import com.love.order.bo.*;
import com.love.order.client.AfterSalesRecordClient;
import com.love.order.client.OrderFeignClient;
import com.love.order.dto.*;
import com.love.order.enums.AfterSaleStatus;
import com.love.order.enums.BuyerType;
import com.love.order.enums.OrderStatus;
import com.love.order.enums.RefundStatus;
import com.love.payment.bo.PaymentDetailBO;
import com.love.payment.client.RefundFeignClient;
import com.love.payment.dto.PaymentRefundDetailDTO;
import com.love.shipment.bo.QueryTracksBO;
import com.love.shipment.client.ShipmentFeignClient;
import com.love.shipment.dto.QueryTracksDTO;
import com.love.user.client.UserFeignClient;
import com.love.user.sdk.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderManager {
    private final UserFeignClient userFeignClient;
    private final OrderFeignClient orderFeignClient;
    private final GoodsFeignClient goodsFeignClient;
    private final GoodsSkuFeignClient goodsSkuFeignClient;
    private final ShipmentFeignClient shipmentFeignClient;
    private final AfterSalesRecordClient afterSalesRecordClient;
    private final RefundFeignClient refundFeignClient;

    private final PostProcessor<MerchantOrderDTO, MerchantOrderVO> merchantOrderPostProcessor = new PostProcessor<MerchantOrderDTO, MerchantOrderVO>() {
        @Override
        public void process(MerchantOrderDTO src, MerchantOrderVO dst) {

            Map<Long, AfterSalesSkuRecordDTO> skuAfterSalesMap = new HashMap<>();
            List<AfterSalesSkuRecordDTO> afterSalesSkuRecords = afterSalesRecordClient.queryLastAfterSaleSkuList(AfterSalesRecordQueryBO.builder().orderNo(src.getOrderNo()).build());
            if (Objects.nonNull(afterSalesSkuRecords)) {
                afterSalesSkuRecords.forEach(afterSalesSkuRecordDTO -> skuAfterSalesMap.put(afterSalesSkuRecordDTO.getSkuId(), afterSalesSkuRecordDTO));
            }

            dst.getItems().forEach(orderItem -> {
                if (Objects.isNull(dst.getShipment()) || Objects.isNull(dst.getShipment().getCarriers())) {
                    dst.setShipment(MerchantOrderVO.Shipment.builder().carriers(orderItem.getCarriers()).trackingNo(orderItem.getTrackingNo()).build());
                }

                GoodsSimpleDTO goodsSimpleDTO = goodsFeignClient.simple(IdParam.builder().id(orderItem.getGoodsId()).build());
                GoodsSkuDTO skuDTO = goodsSkuFeignClient.detail(IdParam.builder().id(orderItem.getSkuId()).build());
                orderItem.setSkuInfo(skuDTO.getAttrValues());
                orderItem.setSkuImg(ObjectUtil.ifNull(skuDTO.getCover(), goodsSimpleDTO.getWhiteBgImg()));
                orderItem.setTotal(MathUtil.multiply(orderItem.getQty(), orderItem.getPrice()));

                if (skuAfterSalesMap.containsKey(orderItem.getSkuId())) {
                    AfterSalesSkuRecordDTO afterSalesSkuRecordDTO = skuAfterSalesMap.get(orderItem.getSkuId());
                    orderItem.setStatus(afterSalesSkuRecordDTO.getAfterSalesRecordDTO().getAfterSaleStatus());
                    orderItem.setAfterSalesNo(afterSalesSkuRecordDTO.getAfterSaleNo());
                }

                OrderDTO orderDTO = orderFeignClient.queryByOrderNo(QuerySimpleOrderBO.builder().orderNo(src.getOrderNo()).build());
                dst.setReceiver(MerchantOrderVO.Consignee.builder().name(orderDTO.getConsignee()).build());

                if (orderItem.getStatus() == OrderStatus.PENDING_RECEIPT.getStatus()) {
                    boolean completed = orderFeignClient.checkCompleted(IdParam.builder().id(orderItem.getId()).build());
                    if (completed) {
                        goodsFeignClient.modifySalesVolume(UpdateGoodsSalesVolumeBO.builder().id(orderItem.getGoodsId()).qty(orderItem.getQty()).build());
                    }
                }
            });
        }
    };

    public Pageable<MerchantOrderVO> page(OrderQueryPageParam orderQueryPageParam) {
        OrderQueryPageBO orderQueryPageBO = BeanUtil.copyProperties(orderQueryPageParam, OrderQueryPageBO.class);
        Pageable<MerchantOrderDTO> pageable = orderFeignClient.page(orderQueryPageBO);
        return PageableUtil.toPage(pageable, MerchantOrderVO.class, merchantOrderPostProcessor);
    }

    public MerchantOrderVO detail(IdParam idParam) {
        OrderDTO order = orderFeignClient.merchantOrderDetail(OrderQueryDetailBO.builder().merchantOrderId(idParam.getId()).build());
        MerchantOrderDTO merchantOrderDTO = order.getMerchantOrders().get(0);
        MerchantOrderVO merchantOrderVO = BeanUtil.copyProperties(merchantOrderDTO, MerchantOrderVO.class);
        this.merchantOrderPostProcessor.process(merchantOrderDTO, merchantOrderVO);
        if (order.getBuyerType().equals(BuyerType.LOVE_ACCOUNT.getType())) {
            UserDTO user = userFeignClient.simple(IdParam.builder().id(order.getBuyerId()).build());
            if (Objects.nonNull(user)) {
                merchantOrderVO.setBuyer(MerchantOrderVO.Buyer.builder().id(user.getId()).avatar(user.getAvatar()).name(user.getFullName()).email(user.getEmail()).build());
            }
        }
        merchantOrderVO.setReceiver(MerchantOrderVO.Consignee.builder().name(order.getConsignee()).address(order.getConsigneeAddress()).email(order.getConsigneeEmail()).phone(order.getConsigneePhone()).build());
        return merchantOrderVO;
    }

    public OrderSimpleStatVO simpleStat(OrderSimpleStatParam orderSimpleStatParam) {
        return BeanUtil.copyProperties(orderFeignClient.simpleStat(BeanUtil.copyProperties(orderSimpleStatParam, OrderSimpleStatBO.class)), OrderSimpleStatVO.class);
    }


    public OrderTracksVO tracks(IdParam idParam) {
        OrderTrackInfoDTO order = orderFeignClient.queryTracking(idParam);
        QueryTracksDTO queryTracksDTO = shipmentFeignClient.tracks(QueryTracksBO.builder().trackingNo(order.getTrackingNo()).carriers(order.getCarriers()).build());
        if (Objects.nonNull(queryTracksDTO)) {
            return BeanUtil.copyProperties(queryTracksDTO, OrderTracksVO.class);
        }
        throw BizException.build("There is no tracking info yet");
    }

    public Pageable<AfterSalesRecordVO> afterSalesPage(AfterSalesOrderQueryPageParam afterSalesOrderQueryPageParam) {
        AfterSalesOrderQueryPageBO afterSalesOrderQueryPageBO = BeanUtil.copyProperties(afterSalesOrderQueryPageParam, AfterSalesOrderQueryPageBO.class);
        Pageable<AfterSalesRecordDetailDTO> pageable = afterSalesRecordClient.afterSalesPage(afterSalesOrderQueryPageBO);
        List<String> merOrderNoList = pageable.getRecords().stream().map(r -> r.getMerOrderNo()).collect(Collectors.toList());

        Pageable<AfterSalesRecordVO> afterSalesRecordVOPageable = PageableUtil.toPage(pageable, AfterSalesRecordVO.class);
        if (CollUtil.isNotEmpty(merOrderNoList)) {
            Map<String, Long> orderMap = orderFeignClient.queryMerchantOrderList(QueryMerchantOrderNoListBO.builder().merchantOrderNoList(merOrderNoList).build()).stream().collect(Collectors.toMap(MerchantOrderSimpleDTO::getMerOrderNo, MerchantOrderSimpleDTO::getId));
            afterSalesRecordVOPageable.getRecords().forEach(r -> {
                r.setMerOrderId(orderMap.get(r.getMerOrderNo()));
            });
        }

        return afterSalesRecordVOPageable;
    }

    public AfterSalesDetailVO afterSalesDetail(AfterSalesQueryParam afterSalesOrderQueryPageParam) {
        AfterSalesQueryBO afterSalesQueryBO = BeanUtil.copyProperties(afterSalesOrderQueryPageParam, AfterSalesQueryBO.class);
        AfterSalesRecordDetailDTO afterSalesRecordDetailDTO = afterSalesRecordClient.afterSalesDetail(afterSalesQueryBO);
        AfterSalesDetailVO afterSalesDetailVO = BeanUtil.copyProperties(afterSalesRecordDetailDTO, AfterSalesDetailVO.class);
        UserDTO user = userFeignClient.simple(IdParam.builder().id(afterSalesRecordDetailDTO.getBuyerId()).build());
        if (Objects.nonNull(user)) {
            afterSalesDetailVO.setBuyer(AfterSalesDetailVO.Buyer.builder().name(user.getFullName()).email(user.getEmail()).build());
        }
        OrderDTO orderDTO = orderFeignClient.queryByOrderNo(QuerySimpleOrderBO.builder().orderNo(afterSalesRecordDetailDTO.getOrderNo()).build());
        if (Objects.nonNull(orderDTO)) {
            afterSalesDetailVO.setReceiver(AfterSalesDetailVO.Receiver.builder().name(orderDTO.getConsignee()).phone(orderDTO.getConsigneePhone()).email(orderDTO.getConsigneeEmail()).address(orderDTO.getConsigneeAddress()).build());
        }

        List<MerchantOrderSimpleDTO> merchantOrderSimpleDTOList = orderFeignClient.queryMerchantOrderList(QueryMerchantOrderNoListBO.builder().merchantOrderNoList(Lists.newArrayList(afterSalesDetailVO.getMerOrderNo())).build());
        if (CollUtil.isNotEmpty(merchantOrderSimpleDTOList)) {
            afterSalesDetailVO.setMerOrderId(merchantOrderSimpleDTOList.get(0).getId());
        }

        // check if the refunding order has been refunded successful
        if (afterSalesRecordDetailDTO.getAfterSaleStatus() == AfterSaleStatus.REFUNDING.getStatus()) {
            PaymentRefundDetailDTO paymentRefundDetailDTO = refundFeignClient.getRefundDetail(PaymentDetailBO.builder().orderNo(orderDTO.getOrderNo()).build());
            if (paymentRefundDetailDTO != null) {
                List<PaymentRefundDetailDTO.RefundTransactionDTO> refundTransactionDTOList = paymentRefundDetailDTO.getRefundTransactions().stream().filter(p -> p.getCredit() != null).collect(Collectors.toList());
                if (CollUtil.isNotEmpty(refundTransactionDTOList)) {
                    Map<String, String> refundMap = refundTransactionDTOList.stream().collect(Collectors.toMap(k -> k.getId(), v -> v.getCredit().getStatus()));
                    boolean existRefundSuccess = refundMap.values().stream().anyMatch(k -> k.equals("succeeded"));

                    if (existRefundSuccess) {
                        List<OrderRefundDTO> orderRefundDTOList = afterSalesRecordClient.thirdList(AfterSalesQueryBO.builder().afterSaleNo(afterSalesOrderQueryPageParam.getAfterSaleNo()).build());
                        for (OrderRefundDTO orderRefundDTO : orderRefundDTOList) {
                            String status = refundMap.get(orderRefundDTO.getThirdRefundNo());
                            if (StringUtils.isNotBlank(status) && status.equals("succeeded")) {
                                agreeRefundCallback(afterSalesOrderQueryPageParam.getAfterSaleNo(), paymentRefundDetailDTO.getId(), RefundStatus.REFUND_SUCCESS.getType());
                                break;
                            }
                        }
                    }
                }
            }
        }

        return afterSalesDetailVO;
    }

    private void agreeRefundCallback(String afterSaleNo, String thirdRefundNo, Integer refundStatus) {
        afterSalesRecordClient.merchantAgreeRefundCallBack(AfterSalesMerchantAgreeCallBackBO.builder()
                .afterSaleNo(afterSaleNo).thirdRefundNo(thirdRefundNo).refundStatus(RefundStatus.getByType(refundStatus))
                .build());
    }
}
