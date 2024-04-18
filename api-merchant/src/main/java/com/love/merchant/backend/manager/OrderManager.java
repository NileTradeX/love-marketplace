package com.love.merchant.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.love.common.bo.EmailOrderItemBO;
import com.love.common.bo.ShopperOrderShippedEmailSendBO;
import com.love.common.client.EmailSendFeignClient;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.common.util.*;
import com.love.goods.bo.ModifyGoodsSkuCommittedStockBO;
import com.love.goods.bo.UpdateGoodsSalesVolumeBO;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.client.GoodsSkuFeignClient;
import com.love.goods.dto.GoodsSimpleDTO;
import com.love.goods.dto.GoodsSkuDTO;
import com.love.influencer.bo.InfUserOrderRefundBO;
import com.love.influencer.client.InfUserOrderFeignClient;
import com.love.merchant.backend.model.param.*;
import com.love.merchant.backend.model.vo.*;
import com.love.merchant.bo.MerUserAdminQueryBO;
import com.love.merchant.client.MerchantUserFeignClient;
import com.love.order.bo.*;
import com.love.order.client.AfterSalesRecordClient;
import com.love.order.client.OrderFeignClient;
import com.love.order.dto.*;
import com.love.order.enums.AfterSaleStatus;
import com.love.order.enums.BuyerType;
import com.love.order.enums.OrderStatus;
import com.love.order.enums.RefundStatus;
import com.love.payment.bo.PaymentDetailBO;
import com.love.payment.bo.PaymentRefundBO;
import com.love.payment.client.RefundFeignClient;
import com.love.payment.dto.PaymentRefundDTO;
import com.love.payment.dto.PaymentRefundDetailDTO;
import com.love.shipment.bo.QueryTracksBO;
import com.love.shipment.bo.ShippoTrackBO;
import com.love.shipment.client.ShipmentFeignClient;
import com.love.shipment.dto.QueryTracksDTO;
import com.love.user.client.UserFeignClient;
import com.love.user.sdk.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.love.common.Constants.S3_HOST;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderManager {
    private final Logger logger = LoggerFactory.getLogger(OrderManager.class);

    private final UserFeignClient userFeignClient;
    private final OrderFeignClient orderFeignClient;
    private final GoodsFeignClient goodsFeignClient;
    private final RefundFeignClient refundFeignClient;
    private final GoodsSkuFeignClient goodsSkuFeignClient;
    private final ShipmentFeignClient shipmentFeignClient;
    private final AfterSalesRecordClient afterSalesRecordClient;
    private final MerchantUserFeignClient merchantUserFeignClient;
    private final EmailSendFeignClient emailSendFeignClient;
    private final InfUserOrderFeignClient influencerOrderFeignClient;

    @Value("${app.customer.order-view-url}")
    private String customerOrderViewUrl;

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

    private Long getAdminId(Long userId) {
        return merchantUserFeignClient.queryAdminById(MerUserAdminQueryBO.builder().userId(userId).simple(true).build()).getId();
    }

    public Pageable<MerchantOrderVO> page(OrderQueryPageParam orderQueryPageParam) {
        OrderQueryPageBO orderQueryPageBO = BeanUtil.copyProperties(orderQueryPageParam, OrderQueryPageBO.class);
        orderQueryPageBO.setMerchantId(getAdminId(orderQueryPageParam.getUserId()));
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
        return BeanUtil.copyProperties(orderFeignClient.simpleStat(OrderSimpleStatBO.builder().merchantId(getAdminId(orderSimpleStatParam.getUserId())).build()), OrderSimpleStatVO.class);
    }

    public OrderTracksVO tracks(IdParam idParam) {
        OrderTrackInfoDTO order = orderFeignClient.queryTracking(idParam);
        QueryTracksDTO queryTracksDTO = shipmentFeignClient.tracks(QueryTracksBO.builder().trackingNo(order.getTrackingNo()).carriers(order.getCarriers()).build());
        if (Objects.nonNull(queryTracksDTO)) {
            return BeanUtil.copyProperties(queryTracksDTO, OrderTracksVO.class);
        }
        throw BizException.build("this is no tracking info yet");
    }

    public Boolean saveTracking(OrderTrackingInfoParam orderTrackingInfoParam) {
        OrderTrackingInfoBO orderTrackingInfoBO = BeanUtil.copyProperties(orderTrackingInfoParam, OrderTrackingInfoBO.class);

        OrderDTO orderDTO = orderFeignClient.merchantOrderDetail(OrderQueryDetailBO.builder().merchantOrderId(orderTrackingInfoBO.getMerchantOrderId()).build());
        sendOrderShippedEmail(orderTrackingInfoBO, orderDTO);
        orderFeignClient.saveTracking(orderTrackingInfoBO);
        registerHook(orderTrackingInfoParam);
        return Boolean.TRUE;
    }

    public Boolean updateTracking(OrderTrackingInfoParam orderTrackingInfoParam) {
        OrderTrackingInfoBO orderTrackingInfoBO = BeanUtil.copyProperties(orderTrackingInfoParam, OrderTrackingInfoBO.class);
        orderFeignClient.updateTracking(orderTrackingInfoBO);
        registerHook(orderTrackingInfoParam);
        return Boolean.TRUE;
    }

    private void registerHook(OrderTrackingInfoParam orderTrackingInfoParam) {
        try{
            shipmentFeignClient.registerHook(ShippoTrackBO.builder().trackingNo(orderTrackingInfoParam.getTrackingNo())
                    .carriers(orderTrackingInfoParam.getCarriers()).merchantOrderId(orderTrackingInfoParam.getMerchantOrderId()).build());
        }catch (Throwable throwable){
            log.error("registerHook error! {}", orderTrackingInfoParam,throwable);
        }
    }

    public OrderTrackInfoVO queryTracking(IdParam idParam) {
        OrderTrackInfoDTO orderTrackInfo = orderFeignClient.queryTracking(idParam);
        return BeanUtil.copyProperties(orderTrackInfo, OrderTrackInfoVO.class);
    }

    public Pageable<AfterSalesRecordVO> afterSalesPage(AfterSalesOrderQueryPageParam afterSalesOrderQueryPageParam) {
        AfterSalesOrderQueryPageBO afterSalesOrderQueryPageBO = BeanUtil.copyProperties(afterSalesOrderQueryPageParam, AfterSalesOrderQueryPageBO.class);
        afterSalesOrderQueryPageBO.setMerchantId(getAdminId(afterSalesOrderQueryPageParam.getUserId()));
        Pageable<AfterSalesRecordDetailDTO> pageable = afterSalesRecordClient.afterSalesPage(afterSalesOrderQueryPageBO);
        List<String> merOrderNoList = pageable.getRecords().stream().map(AfterSalesRecordDetailDTO::getMerOrderNo).collect(Collectors.toList());

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

    public Boolean agreeOrderRefund(OrderRefundAgreeParam orderRefundAgreeParam) {
        AfterSalesRecordDetailDTO afterSalesRecordDetailDTO = afterSalesRecordClient.afterSalesDetail(AfterSalesQueryBO.builder().afterSaleNo(orderRefundAgreeParam.getAfterSaleNo()).build());
        if (afterSalesRecordDetailDTO == null) {
            throw BizException.build("the after sales record does not exist");
        }

        afterSalesRecordClient.merchantAgreeRefund(AfterSalesMerchantAgreeBO.builder().afterSaleNo(orderRefundAgreeParam.getAfterSaleNo()).refundAmount(afterSalesRecordDetailDTO.getRefundAmount().toString()).build());

        PaymentRefundDTO paymentRefund = null;
        boolean finishRefund = false;
        int maxRetryCount = 3;
        int retryCount = 0;

        // refund max 3 times if bolt refund return fail
        while (!finishRefund && retryCount++ < maxRetryCount) {
            try {
                paymentRefund = refundFeignClient.create(PaymentRefundBO.builder().orderNo(orderRefundAgreeParam.getOrderNo()).refundFee(MathUtil.multiply_100(afterSalesRecordDetailDTO.getRefundAmount().toString()).longValue()).build());

                if (paymentRefund.getStatus() == RefundStatus.REFUND_FAIL.getType()) {
                    if (retryCount == maxRetryCount) {
                        agreeRefundCallback(orderRefundAgreeParam.getAfterSaleNo(), paymentRefund.getId(), paymentRefund.getStatus());
                    }
                } else {
                    agreeRefundCallback(orderRefundAgreeParam.getAfterSaleNo(), paymentRefund.getId(), paymentRefund.getStatus());
                    finishRefund = true;
                }
            } catch (Exception e) {
                log.error("refund error", e);
                afterSalesRecordClient.merchantAgreeRefundCallBack(AfterSalesMerchantAgreeCallBackBO.builder().afterSaleNo(orderRefundAgreeParam.getAfterSaleNo()).refundStatus(RefundStatus.REFUND_FAIL).build());

                finishRefund = true;
            }
        }

        boolean refundFlag = paymentRefund != null && paymentRefund.getStatus() == RefundStatus.REFUND_SUCCESS.getType();

        OrderSimpleDTO orderSimpleDTO = orderFeignClient.simple(QuerySimpleOrderBO.builder().orderNo(orderRefundAgreeParam.getOrderNo()).build());
        if (refundFlag && orderSimpleDTO.getStatus().equals(OrderStatus.PENDING_SHIPMENT.getStatus())) {
            afterSalesRecordClient.afterSaleSkuList(AfterSalesQueryBO.builder().afterSaleNo(orderRefundAgreeParam.getAfterSaleNo()).build()).forEach(k -> {
                goodsSkuFeignClient.modifyCommittedStock(ModifyGoodsSkuCommittedStockBO.builder().skuId(k.getSkuId()).committedStock(k.getQty() * -1).build());
            });
        }

        if (refundFlag) {
            AfterSalesRecordDetailDTO afterSalesDetail = afterSalesRecordClient.afterSalesDetail(AfterSalesQueryBO.builder().afterSaleNo(orderRefundAgreeParam.getAfterSaleNo()).build());
            InfUserOrderRefundBO infUserOrderRefundBO = InfUserOrderRefundBO.builder()
                    .orderId(orderSimpleDTO.getId())
                    .items(afterSalesDetail.getItems().stream().map(e -> InfUserOrderRefundBO.Item.builder().skuId(e.getSkuId()).refundAmount(e.getRefundAmount()).build()).collect(Collectors.toList()))
                    .build();
            log.info("handle influencer refund:{}", infUserOrderRefundBO);
            influencerOrderFeignClient.refund(infUserOrderRefundBO);
        }

        return refundFlag;
    }

    private void agreeRefundCallback(String afterSaleNo, String thirdRefundNo, Integer refundStatus) {
        afterSalesRecordClient.merchantAgreeRefundCallBack(AfterSalesMerchantAgreeCallBackBO.builder()
                .afterSaleNo(afterSaleNo).thirdRefundNo(thirdRefundNo).refundStatus(RefundStatus.getByType(refundStatus))
                .build());
    }

    public Boolean declineOrderRefund(OrderRefundDeclineParam orderRefundDeclineParam) {
        AfterSalesMerchantRejectBO afterSalesMerchantRejectBO = AfterSalesMerchantRejectBO.builder()
                .afterSaleNo(orderRefundDeclineParam.getAfterSaleNo())
                .afterSaleRemark(orderRefundDeclineParam.getMerchantDealDesc())
                .build();
        afterSalesRecordClient.merchantDeclineRefund(afterSalesMerchantRejectBO);
        return Boolean.TRUE;
    }



    private void sendOrderShippedEmail(OrderTrackingInfoBO orderTrackingInfoBO, OrderDTO orderDTO) {
        List<EmailOrderItemBO> emailOrderItems = new ArrayList<>();

        for (OrderItemDTO orderItemDTO : orderDTO.getMerchantOrders().get(0).getItems()) {
            GoodsSimpleDTO goodsSimpleDTO = goodsFeignClient.simple(IdParam.builder().id(orderItemDTO.getGoodsId()).build());
            GoodsSkuDTO goodsSkuDTO = goodsSkuFeignClient.detail(IdParam.builder().id(orderItemDTO.getSkuId()).build());

            String itemTotalAmount = String.valueOf(orderItemDTO.getPrice().multiply(new BigDecimal(orderItemDTO.getQty())).setScale(2));

            EmailOrderItemBO emailOrderItemBO = EmailOrderItemBO.builder()
                    .goodsTitle(orderItemDTO.getGoodsTitle())
                    .goodsImage(S3_HOST + goodsSimpleDTO.getWhiteBgImg())
                    .skuId(String.valueOf(orderItemDTO.getSkuId()))
                    .skuSpec(goodsSkuDTO.getAttrValues())
                    .unitPrice(String.valueOf(orderItemDTO.getPrice()))
                    .quantity(String.valueOf(orderItemDTO.getQty()))
                    .itemTotalAmount(itemTotalAmount)
                    .build();

            emailOrderItems.add(emailOrderItemBO);
        }

        JSONObject addrJson = JSONObject.parseObject(orderDTO.getConsigneeAddress());
        String emailShippingAddress = String.format("%s, %s, %s %s",
                addrJson.get("address"), addrJson.get("city"), addrJson.get("state"), addrJson.get("zipCode"));


        ShopperOrderShippedEmailSendBO shopperOrderShippedEmailSendBO = ShopperOrderShippedEmailSendBO.builder()
                .toEmail(orderDTO.getConsigneeEmail())
                .carrier(orderTrackingInfoBO.getCarriers())
                .trackingNumber(orderTrackingInfoBO.getTrackingNo())
                .shippingAddress(emailShippingAddress)
                .shippingMethod("Free Shipping") // This is hardcoded because we only provide Free Shipping (as this code is written)
                .orderDate(DateUtil.format(orderDTO.getCreateTime()))
                .viewUrl(customerOrderViewUrl + orderDTO.getMerchantOrders().get(0).getMerOrderNo())
                .items(emailOrderItems)
                .build();

        try {
            emailSendFeignClient.sendShopperOrderShippedEmail(shopperOrderShippedEmailSendBO);
        } catch (RuntimeException e) {
            logger.error("===> failed to send order shipped email to shopper: {}", e.getMessage());
        }
    }

}
