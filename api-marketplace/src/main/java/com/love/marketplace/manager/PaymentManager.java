package com.love.marketplace.manager;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.com.google.common.base.Strings;
import com.love.common.bo.*;
import com.love.common.client.EmailSendFeignClient;
import com.love.common.client.KeyValueFeignClient;
import com.love.common.dto.KeyValueDTO;
import com.love.common.exception.BizException;
import com.love.common.param.IdParam;
import com.love.common.util.*;
import com.love.goods.bo.ModifyGoodsSkuCommittedStockBO;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.client.GoodsSkuFeignClient;
import com.love.goods.dto.GoodsSimpleDTO;
import com.love.goods.dto.GoodsSkuDTO;
import com.love.influencer.bo.InfGoodsSalesVolumeUpdateBO;
import com.love.influencer.client.InfGoodsFeignClient;
import com.love.marketplace.model.dto.FreeGiftConfig;
import com.love.marketplace.model.param.*;
import com.love.marketplace.model.vo.PaymentCreateVO;
import com.love.marketplace.model.vo.PaymentStatusVO;
import com.love.merchant.bo.MerQueryByAdminIdBO;
import com.love.merchant.bo.MerUserAdminQueryBO;
import com.love.merchant.client.CommissionRateFeignClient;
import com.love.merchant.client.MerUserBusinessInfoFeignClient;
import com.love.merchant.client.MerchantUserFeignClient;
import com.love.merchant.dto.MerUserAdminDTO;
import com.love.order.bo.OrderUpdateByOrderNoBO;
import com.love.order.bo.QuerySimpleOrderBO;
import com.love.order.client.OrderFeignClient;
import com.love.order.client.ShopifyFeignClient;
import com.love.order.dto.*;
import com.love.order.enums.BuyerType;
import com.love.order.enums.OrderStatus;
import com.love.order.bo.ShopifyOrderCreateBO;
import com.love.payment.bo.PaymentCreateBO;
import com.love.payment.bo.PaymentDetailBO;
import com.love.payment.bo.PaymentUpdateByOrderNoBO;
import com.love.payment.bo.SplitFundsBO;
import com.love.payment.client.PaymentFeignClient;
import com.love.payment.dto.PaymentCreateDTO;
import com.love.payment.dto.PaymentDetailDTO;
import com.love.payment.dto.SplitFundsResultDTO;
import com.love.payment.enums.SplitType;
import com.love.user.client.GuestFeignClient;
import com.love.user.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.love.common.Constants.KEY_FREE_GIFT_CONFIG;
import static com.love.common.Constants.S3_HOST;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PaymentManager {

    private final Logger logger = LoggerFactory.getLogger(PaymentManager.class);

    private final PaymentFeignClient paymentFeignClient;
    private final OrderFeignClient orderFeignClient;
    private final ShopifyFeignClient shopifyFeignClient;
    private final GoodsFeignClient goodsFeignClient;
    private final GoodsSkuFeignClient goodsSkuFeignClient;
    private final EmailSendFeignClient emailSendFeignClient;
    private final CommissionRateFeignClient commissionRateFeignClient;
    private final InfGoodsFeignClient influencerGoodsFeignClient;
    private final MerchantUserFeignClient merchantUserFeignClient;
    private final KeyValueFeignClient keyValueFeignClient;
    private final UserFeignClient userFeignClient;
    private final MerUserBusinessInfoFeignClient merUserBusinessInfoFeignClient;
    private final GuestFeignClient guestFeignClient;
    private final RedisUtil redisUtil;

    private final TaskExecutor taskExecutor;

    @Value("${app.customer.order-view-url}")
    private String customerOrderViewUrl;
    @Value("${app.merchant.order-view-url}")
    private String merchantOrderViewUrl;

    public PaymentCreateVO create(PaymentCreateParam paymentCreateParam) {
        logger.error("===> {}", paymentCreateParam);

        OrderCreateDTO order;
        String data = paymentCreateParam.getExt();
        try {
            order = GsonUtil.json2bean(EncryptUtil.aesDecrypt(data), OrderCreateDTO.class);
        } catch (Exception ex) {
            throw BizException.build("payment security check failed");
        }

        Long amount = MathUtil.multiply_100(order.getAmount()).longValue();
        Long appFee = MathUtil.multiply_100(order.getAppFee()).longValue();

        PaymentCreateBO paymentCreateBO = PaymentCreateBO.builder()
                .orderNo(order.getOrderNo())
                .amount(amount)
                .appFee(appFee)
                .userId(order.getUserId())
                .merchantId(order.getMerchantId())
                .build();

        try {
            PaymentCreateDTO payment = paymentFeignClient.create(paymentCreateBO);
            if (Objects.nonNull(payment) && Objects.nonNull(payment.getId())) {
                return PaymentCreateVO.builder().clientSecret(payment.getClientSecret()).build();
            }
        } catch (Exception ex) {
            logger.error("===> prepare payment meet error : {}", ex.getMessage());
        }
        throw BizException.build("prepare payment failed");
    }

    public PaymentStatusVO status(PaymentStatusParam paymentStatusParam) {
        PaymentDetailBO paymentDetailBO = PaymentDetailBO.builder().paymentId(paymentStatusParam.getPaymentId()).build();
        PaymentDetailDTO paymentDetailDTO = paymentFeignClient.detail(paymentDetailBO);
        QuerySimpleOrderBO queryByOrderNoBO = QuerySimpleOrderBO.builder().orderNo(paymentDetailDTO.getOrderNo()).build();
        OrderDTO orderDTO = orderFeignClient.queryByOrderNo(queryByOrderNoBO);
        PaymentStatusVO paymentStatusVO = BeanUtil.copyProperties(paymentDetailDTO, PaymentStatusVO.class);
        paymentStatusVO.setOrderId(orderDTO.getId());
        paymentStatusVO.setTotalAmount(orderDTO.getTotalAmount());
        paymentStatusVO.setOrderTotalCurrency("USD");
        return paymentStatusVO;
    }

    public void createPay(PayInfoCreateParam payInfoCreateParam) {
        QuerySimpleOrderBO queryByOrderNoBO = QuerySimpleOrderBO.builder().orderNo(payInfoCreateParam.getOrderNo()).build();
        OrderDTO orderDTO = orderFeignClient.queryByOrderNo(queryByOrderNoBO);

        PaymentCreateBO paymentCreateBO = PaymentCreateBO.builder()
                .orderNo(payInfoCreateParam.getOrderNo())
                .amount(payInfoCreateParam.getTotalAmount())
                .appFee(payInfoCreateParam.getAppFee())
                .userId(orderDTO.getBuyerId())
                .channel(payInfoCreateParam.getChannel())
                .paymentId(payInfoCreateParam.getPaymentId())
                .build();
        paymentFeignClient.createPay(paymentCreateBO);
    }

    public void paymentSuccess(PaymentResultParam paymentResultParam) {
        String orderNo = paymentResultParam.getOrderNo();

        PaymentUpdateByOrderNoBO paymentUpdateByOrderNoBO = new PaymentUpdateByOrderNoBO();
        paymentUpdateByOrderNoBO.setOrderNo(orderNo);
        paymentUpdateByOrderNoBO.setType(paymentResultParam.getType());
        paymentUpdateByOrderNoBO.setStatus(paymentResultParam.getStatus());
        paymentUpdateByOrderNoBO.setPayTime(paymentResultParam.getPayTime());
        paymentFeignClient.updateByOrderNo(paymentUpdateByOrderNoBO);

        OrderUpdateByOrderNoBO orderUpdateByOrderNoBO = new OrderUpdateByOrderNoBO();
        orderUpdateByOrderNoBO.setOrderNo(orderNo);
        orderUpdateByOrderNoBO.setStatus(OrderStatus.PENDING_SHIPMENT.getStatus());
        orderUpdateByOrderNoBO.setSkuIdList(paymentResultParam.getSkuIdList());
        orderUpdateByOrderNoBO.setCascade(true);
        orderFeignClient.updateByOrderNo(orderUpdateByOrderNoBO);

        OrderDTO order = orderFeignClient.queryByOrderNo(QuerySimpleOrderBO.builder().orderNo(orderNo).build());

        order.getMerchantOrders().forEach(merchantOrderDTO ->
                merchantOrderDTO.getItems()
                        .forEach(orderItemDTO -> influencerGoodsFeignClient
                                .modifySalesVolume(new InfGoodsSalesVolumeUpdateBO(
                                        orderItemDTO.getGoodsId(),
                                        orderItemDTO.getQty()))));

        // Send email and shopify notification may take a while.
        // This could cause bolt to resend payment confirmation request.
        // So we make it async here.
        taskExecutor.execute(() -> {
            try {
                sendEmailAndShopifyNotification(order);

                String email;
                if (BuyerType.LOVE_ACCOUNT.getType() == order.getBuyerType()) {
                    email = userFeignClient.simple(IdParam.builder().id(order.getBuyerId()).build()).getEmail();
                } else {
                    email = paymentResultParam.getEmail();
                }

                if (Objects.isNull(redisUtil.get("free::gift::" + email))) {
                    KeyValueDTO keyValueDTO = keyValueFeignClient.queryByKey(
                            KeyQueryBO.builder().key(KEY_FREE_GIFT_CONFIG).build());
                    FreeGiftConfig config = GsonUtil.json2bean(keyValueDTO.getValue(), FreeGiftConfig.class);
                    if (config.checkActivityPeriod() && (paymentResultParam.getAmount() >= config.getAmount().intValue())) {
                        emailSendFeignClient.sendFreeGiftEmail(
                                CustomerFreeGiftSendBO.builder()
                                        .toEmail(email)
                                        .promoCode(config.getCode())
                                        .promoProductUrl(config.getProductUrl())
                                        .build());
                        redisUtil.set("free::gift::" + email, 1, 3600 * 24 * 120);
                    }
                }
            } catch (Throwable throwable) {
                logger.error("Failed to send notification for order: {}", order, throwable);
            }
        });
    }

    public void sendEmailAndShopifyNotification(OrderDTO order) {
        for (MerchantOrderDTO merChantOrder : order.getMerchantOrders()) {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                logger.error("sleep error: ", e);
            }
            List<EmailOrderItemBO> emailOrderItemsInOneMerchant = new ArrayList<>();

            for (OrderItemDTO orderItemDTO : merChantOrder.getItems()) {
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

                emailOrderItemsInOneMerchant.add(emailOrderItemBO);
            }

            try {
                sendEmailToMerchant(merChantOrder, emailOrderItemsInOneMerchant);
            } catch (RuntimeException e) {
                logger.error("===> failed to send order summary email to merchant: {}", e.getMessage());
            }

            try {
                sendEmailToShopper(order, emailOrderItemsInOneMerchant, merChantOrder);
            } catch (RuntimeException e) {
                logger.error("===> failed to send order summary email to shopper: {}", e.getMessage());
            }

            try {
                String merchantShopName = merUserBusinessInfoFeignClient
                        .queryByAdminId(new MerQueryByAdminIdBO(merChantOrder.getMerchantId()))
                        .getBizName();
                // Push notification to Shopify if the merchant comes from Shopify
                if (!Strings.isNullOrEmpty(merchantShopName)) {
                    ShopifyOrderCreateBO shopifyOrderCreateBO = new ShopifyOrderCreateBO(
                            BeanUtil.copyProperties(order, ShopifyOrderCreateBO.Order.class),
                            BeanUtil.copyToList(emailOrderItemsInOneMerchant, ShopifyOrderCreateBO.OrderItem.class),
                            merchantShopName);
                    logger.info("=== [{}] shopify order {}",
                            shopifyFeignClient.shopifyOrderCreate(shopifyOrderCreateBO),
                            shopifyOrderCreateBO);
                }
            } catch (RuntimeException e) {
                logger.error("===> failed to create shopify order for merchant: {}", e.getMessage());
            }
        }
    }

    private void sendEmailToMerchant(MerchantOrderDTO merChantOrder, List<EmailOrderItemBO> emailOrderItemsInOneMerchant) {
        MerUserAdminDTO merUserAdminDTO = merchantUserFeignClient.queryAdminById(MerUserAdminQueryBO.builder().userId(merChantOrder.getMerchantId()).simple(false).build());

        MerchantOrderSummaryEmailSendBO merchantOrderSummaryEmailSendBO = MerchantOrderSummaryEmailSendBO.builder()
                .toEmail(merUserAdminDTO.getBusinessInfo().getBizOrderMgmtEmail())
                .merchantName(merUserAdminDTO.getBusinessInfo().getBizName())
                .merchantOrderNumber(merChantOrder.getMerOrderNo())
                .orderDate(DateUtil.format(merChantOrder.getCreateTime()))
                .items(emailOrderItemsInOneMerchant)
                .totalAmount(String.valueOf(merChantOrder.getTotalAmount()))
                .viewUrl(merchantOrderViewUrl)
                .build();

        emailSendFeignClient.sendMerchantOrderSummaryEmail(merchantOrderSummaryEmailSendBO);
    }

    private void sendEmailToShopper(OrderDTO orderDTO, List<EmailOrderItemBO> allEmailOrderItems, MerchantOrderDTO merChantOrder) {
        JSONObject addrJson = JSONObject.parseObject(orderDTO.getConsigneeAddress());
        String emailShippingAddress = String.format("%s, %s, %s %s",
                addrJson.get("address"), addrJson.get("city"), addrJson.get("state"), addrJson.get("zipCode"));

        ShopperOrderSummaryEmailSendBO shopperOrderSummaryEmailSendBO = ShopperOrderSummaryEmailSendBO.builder()
                .toEmail(orderDTO.getConsigneeEmail())
                .merchantOrderNumber(merChantOrder.getMerOrderNo())
                .orderDate(DateUtil.format(orderDTO.getCreateTime()))
                .items(allEmailOrderItems)
                .shippingMethod("Free Shipping")
                .shippingFee(String.valueOf(orderDTO.getShippingFee()))
                .taxes(String.valueOf(orderDTO.getTaxes()))
                .shippingAddress(emailShippingAddress)
                .totalAmount(String.valueOf(merChantOrder.getTotalAmount()))
                .viewUrl(customerOrderViewUrl + merChantOrder.getMerOrderNo())
                .build();

        emailSendFeignClient.sendShopperOrderSummaryEmail(shopperOrderSummaryEmailSendBO);

    }


    public void paymentFiled(PaymentResultParam paymentResultParam) {
        String orderNo = paymentResultParam.getOrderNo();

        PaymentUpdateByOrderNoBO paymentUpdateByOrderNoBO = new PaymentUpdateByOrderNoBO();
        paymentUpdateByOrderNoBO.setOrderNo(orderNo);
        paymentUpdateByOrderNoBO.setType(paymentResultParam.getType());
        paymentUpdateByOrderNoBO.setStatus(paymentResultParam.getStatus());
        paymentUpdateByOrderNoBO.setPayTime(paymentResultParam.getPayTime());
        paymentFeignClient.updateByOrderNo(paymentUpdateByOrderNoBO);

        OrderUpdateByOrderNoBO orderUpdateByOrderNoBO = new OrderUpdateByOrderNoBO();
        orderUpdateByOrderNoBO.setOrderNo(orderNo);
        orderUpdateByOrderNoBO.setStatus(OrderStatus.CLOSED.getStatus());
        orderUpdateByOrderNoBO.setSkuIdList(paymentResultParam.getSkuIdList());
        orderUpdateByOrderNoBO.setReason("Payment failed.");
        orderUpdateByOrderNoBO.setCascade(true);
        orderFeignClient.updateByOrderNo(orderUpdateByOrderNoBO);

        OrderDTO order = orderFeignClient.queryByOrderNo(QuerySimpleOrderBO.builder().orderNo(orderNo).build());
        order.getMerchantOrders().forEach(merchantOrderDTO -> merchantOrderDTO.getItems().forEach(orderItemDTO -> goodsSkuFeignClient.modifyCommittedStock(ModifyGoodsSkuCommittedStockBO.builder().skuId(orderItemDTO.getSkuId()).committedStock(orderItemDTO.getQty() * -1).build())));
    }

    public void adyenSplitFunds(AdyenSplitFundsParam splitFundsParam) {
        Long amount = splitFundsParam.getAmount();

        SplitFundsBO splitFundsBO = new SplitFundsBO();
        splitFundsBO.setMerchantReference(splitFundsParam.getMerchantReference());
        splitFundsBO.setPspReference(splitFundsParam.getPspReference());
        splitFundsBO.setTotalAmount(amount);
        splitFundsBO.setCurrency(splitFundsParam.getCurrency());

        List<SplitFundsBO.SplitItem> splitItems = new ArrayList<>();

        long loveAmount = 0L;
        SplitFundsBO.SplitItem love = new SplitFundsBO.SplitItem();
        love.setType(SplitType.COMMISSION.getValue());
        love.setReference(RandomUtil.randomStr(16));
        splitItems.add(love);

        OrderDTO order = orderFeignClient.queryByOrderNo(QuerySimpleOrderBO.builder().orderNo(splitFundsParam.getOrderNo()).build());
        List<MerchantOrderDTO> merchantOrders = order.getMerchantOrders();
        for (MerchantOrderDTO merchantOrder : merchantOrders) {
            BigDecimal rate = commissionRateFeignClient.queryCurrent(merchantOrder.getMerchantId());
            long toLove = MathUtil.multiply(merchantOrder.getTotalAmount().multiply(new BigDecimal("100")), rate).divide(new BigDecimal("100"), RoundingMode.HALF_UP).longValue();

            SplitFundsBO.SplitItem merchant = new SplitFundsBO.SplitItem();
            merchant.setType(SplitType.BALANCEACCOUNT.getValue());
            merchant.setMerchantId(merchantOrder.getMerchantId());
            merchant.setRate(MathUtil.subtract(new BigDecimal("100"), rate));
            merchant.setReference(RandomUtil.randomStr(16));
            merchant.setAmount(MathUtil.subtract(merchantOrder.getTotalAmount().multiply(new BigDecimal("100")), toLove).longValue());
            splitItems.add(merchant);

            loveAmount += toLove;
        }

        love.setAmount(loveAmount);
        splitFundsBO.setItems(splitItems);
        logger.error("=====> split data: {}", GsonUtil.bean2json(splitFundsBO));
        SplitFundsResultDTO splitFundsResultDTO = paymentFeignClient.splitFunds(splitFundsBO);
        logger.error("=====> split result: {}", GsonUtil.bean2json(splitFundsResultDTO));
    }
}
