package com.love.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.love.common.bo.*;
import com.love.common.config.CustomerIoProperties;
import com.love.common.dto.EmailSendDTO;
import com.love.common.service.EmailSendService;
import com.love.common.util.HttpUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerIoEmailSendServiceImpl implements EmailSendService {

    private final Logger logger = LoggerFactory.getLogger(CustomerIoEmailSendServiceImpl.class);

    private final CustomerIoProperties customerIoProperties;

    @Override
    public EmailSendDTO send(EmailSendBO emailSendBO) {
        JSONObject content = new JSONObject();
        content.put("transactional_message_id", emailSendBO.getTemplateAlias());
        content.put("to", emailSendBO.getToEmail());
        if (StringUtils.isNotBlank(emailSendBO.getSubject())) {
            content.put("subject", emailSendBO.getSubject());
        }
        content.put("message_data", emailSendBO.getTemplateModel());
        JSONObject object = new JSONObject();
        object.put("email", emailSendBO.getToEmail());
        content.put("identifiers", object);

        logger.info("===> send with param : {}", content);

        try {
            String responseJson = HttpUtil.post(customerIoProperties.getApiUrl(), content.toJSONString(), headerMap());
            logger.info("=====> content send to email client: {}", content.toJSONString());
            JSONObject jsonObject = JSON.parseObject(responseJson);
            String deliveryId = jsonObject.getString("delivery_id");
            if (Objects.isNull(deliveryId)) {
                logger.warn("===> send email result: {}", responseJson);
            }
            return EmailSendDTO.builder().id(deliveryId).build();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public EmailSendDTO sendWelcomeEmail(WelcomeEmailSendBO welcomeEmailSendBO) {
        Map<String, Object> map = new HashMap<>();
        map.put("action_url", welcomeEmailSendBO.getEmailCheckUrl());
        EmailSendBO emailSendBO = EmailSendBO.builder()
                .toEmail(welcomeEmailSendBO.getToEmail())
                .templateAlias(customerIoProperties.getTemplateAlias().getWelcome())
                .templateModel(map)
                .build();
        return this.send(emailSendBO);
    }

    @Override
    public EmailSendDTO sendPasswordResetEmail(PasswordResetEmailSendBO passwordResetEmailSendBO) {
        Map<String, Object> map = new HashMap<>();
        map.put("action_url", passwordResetEmailSendBO.getPasswordResetUrl());
        EmailSendBO emailSendBO = EmailSendBO.builder()
                .toEmail(passwordResetEmailSendBO.getToEmail())
                .templateAlias(customerIoProperties.getTemplateAlias().getPasswordReset())
                .templateModel(map)
                .build();
        return this.send(emailSendBO);
    }

    @Override
    public EmailSendDTO sendMerchantInvitationEmail(MerchantInvitationEmailSendBO merchantInvitationEmailSendBO) {
        Map<String, Object> map = new HashMap<>();
        map.put("biz_name", merchantInvitationEmailSendBO.getBizName());
        map.put("action_url", merchantInvitationEmailSendBO.getInviteUrl());
        EmailSendBO emailSendBO = EmailSendBO.builder()
                .toEmail(merchantInvitationEmailSendBO.getToEmail())
                .templateAlias(customerIoProperties.getTemplateAlias().getMerchantInvitation())
                .templateModel(map)
                .build();
        return this.send(emailSendBO);
    }

    @Override
    public EmailSendDTO sendMerchantReviewRejectEmail(MerchantReviewRejectEmailSendBO merchantReviewRejectEmailSendBO) {
        Map<String, Object> map = new HashMap<>();
        map.put("biz_name", merchantReviewRejectEmailSendBO.getBizName());
        map.put("action_url", merchantReviewRejectEmailSendBO.getRegisterUrl());
        map.put("reason", merchantReviewRejectEmailSendBO.getReason());
        EmailSendBO emailSendBO = EmailSendBO.builder()
                .toEmail(merchantReviewRejectEmailSendBO.getToEmail())
                .templateAlias(customerIoProperties.getTemplateAlias().getMerchantReviewReject())
                .templateModel(map)
                .build();
        return this.send(emailSendBO);
    }

    @Override
    public EmailSendDTO sendMerchantReviewApproveEmail(MerchantReviewApproveEmailSendBO merchantReviewApproveEmailSendBO) {
        Map<String, Object> map = new HashMap<>();
        map.put("biz_name", merchantReviewApproveEmailSendBO.getBizName());
        map.put("action_url", merchantReviewApproveEmailSendBO.getLoginUrl());
        EmailSendBO emailSendBO = EmailSendBO.builder()
                .toEmail(merchantReviewApproveEmailSendBO.getToEmail())
                .templateAlias(customerIoProperties.getTemplateAlias().getMerchantReviewApprove())
                .templateModel(map)
                .build();
        return this.send(emailSendBO);
    }

    @Override
    public EmailSendDTO sendMerchantOrderSummaryEmail(MerchantOrderSummaryEmailSendBO merchantOrderSummaryEmailSendBO) {
        Map<String, Object> map = new HashMap<>();
        map.put("merchant_name", merchantOrderSummaryEmailSendBO.getMerchantName());
        map.put("order_number", merchantOrderSummaryEmailSendBO.getMerchantOrderNumber());
        map.put("order_date", merchantOrderSummaryEmailSendBO.getOrderDate());
        map.put("total_amount", merchantOrderSummaryEmailSendBO.getTotalAmount());
        map.put("view_url", merchantOrderSummaryEmailSendBO.getViewUrl());

        List<Map<String, Object>> itemsList = emailItemBOListToMapItem(merchantOrderSummaryEmailSendBO.getItems());
        map.put("items", itemsList);

        EmailSendBO emailSendBO = EmailSendBO.builder()
                .toEmail(merchantOrderSummaryEmailSendBO.getToEmail())
                .templateAlias(customerIoProperties.getTemplateAlias().getOrderSummaryMerchant())
                .templateModel(map)
                .build();
        return this.send(emailSendBO);
    }

    @Override
    public EmailSendDTO sendShopperOrderSummaryEmail(ShopperOrderSummaryEmailSendBO shopperOrderSummaryEmailSendBO) {
        Map<String, Object> map = new HashMap<>();
        map.put("order_number", shopperOrderSummaryEmailSendBO.getMerchantOrderNumber());
        map.put("order_date", shopperOrderSummaryEmailSendBO.getOrderDate());
        map.put("shipping_method", shopperOrderSummaryEmailSendBO.getShippingMethod());
        map.put("shipping_fee", shopperOrderSummaryEmailSendBO.getShippingFee());
        map.put("taxes", shopperOrderSummaryEmailSendBO.getTaxes());
        map.put("shipping_address", shopperOrderSummaryEmailSendBO.getShippingAddress());
        map.put("total_amount", shopperOrderSummaryEmailSendBO.getTotalAmount());
        map.put("view_url", shopperOrderSummaryEmailSendBO.getViewUrl());

        List<Map<String, Object>> itemsList = emailItemBOListToMapItem(shopperOrderSummaryEmailSendBO.getItems());
        map.put("items", itemsList);

        EmailSendBO emailSendBO = EmailSendBO.builder()
                .toEmail(shopperOrderSummaryEmailSendBO.getToEmail())
                .templateAlias(customerIoProperties.getTemplateAlias().getOrderSummaryShopper())
                .templateModel(map)
                .build();
        return this.send(emailSendBO);
    }

    @Override
    public EmailSendDTO sendMerchantRefundEmail(MerchantRefundEmailSendBO merchantRefundEmailSendBO) {
        Map<String, Object> map = new HashMap<>();

        map.put("refund_number", merchantRefundEmailSendBO.getRefundRequestNumber());
        map.put("refund_time", merchantRefundEmailSendBO.getRefundTime());
        map.put("order_number", merchantRefundEmailSendBO.getMerchantOrderNumber());
        map.put("refund_total_amount", merchantRefundEmailSendBO.getRefundTotalAmount());
        map.put("view_url", merchantRefundEmailSendBO.getViewUrl());

        List<Map<String, Object>> itemsList = emailItemBOListToMapItem(merchantRefundEmailSendBO.getItems());
        map.put("items", itemsList);

        EmailSendBO emailSendBO = EmailSendBO.builder()
                .toEmail(merchantRefundEmailSendBO.getToEmail())
                .templateAlias(customerIoProperties.getTemplateAlias().getRefundMerchant())
                .templateModel(map)
                .build();
        return this.send(emailSendBO);
    }

    @Override
    public EmailSendDTO sendShopperOrderShippedEmail(ShopperOrderShippedEmailSendBO shopperOrderShippedEmailSendBO) {
        Map<String, Object> templateModelMap = new HashMap<>();
        templateModelMap.put("carrier", shopperOrderShippedEmailSendBO.getCarrier());
        templateModelMap.put("tracking_number", shopperOrderShippedEmailSendBO.getTrackingNumber());
        templateModelMap.put("shipping_address", shopperOrderShippedEmailSendBO.getShippingAddress());
        templateModelMap.put("shipping_method", shopperOrderShippedEmailSendBO.getShippingMethod());
        templateModelMap.put("order_date", shopperOrderShippedEmailSendBO.getOrderDate());
        templateModelMap.put("view_url", shopperOrderShippedEmailSendBO.getViewUrl());

        List<Map<String, Object>> itemsList = emailItemBOListToMapItem(shopperOrderShippedEmailSendBO.getItems());
        templateModelMap.put("items", itemsList);

        EmailSendBO emailSendBO = EmailSendBO.builder()
                .toEmail(shopperOrderShippedEmailSendBO.getToEmail())
                .templateAlias(customerIoProperties.getTemplateAlias().getOrderShippedShopper())
                .templateModel(templateModelMap)
                .build();
        return this.send(emailSendBO);
    }

    private List<Map<String, Object>> emailItemBOListToMapItem(List<EmailOrderItemBO> items) {
        List<Map<String, Object>> itemsList = new ArrayList<>();
        for (EmailOrderItemBO item : items) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("goods_title", item.getGoodsTitle());
            itemMap.put("goods_image", item.getGoodsImage());
            itemMap.put("sku_id", item.getSkuId());
            itemMap.put("sku_spec", item.getSkuSpec());
            itemMap.put("unit_price", item.getUnitPrice());
            itemMap.put("quantity", item.getQuantity());
            itemMap.put("item_total_amount", item.getItemTotalAmount());

            itemsList.add(itemMap);
        }

        return itemsList;
    }

    private Map<String, String> headerMap() {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", String.format("Bearer %s", customerIoProperties.getApiToken()));
        header.put("content-type", "application/json");
        return header;
    }

    @Override
    public EmailSendDTO sendFreeGiftEmail(CustomerFreeGiftSendBO customerFreeGiftSendBO) {
        Map<String, Object> map = new HashMap<>();
        map.put("promo_code", customerFreeGiftSendBO.getPromoCode());
        map.put("action_url", customerFreeGiftSendBO.getPromoProductUrl());
        EmailSendBO emailSendBO = EmailSendBO.builder()
                .toEmail(customerFreeGiftSendBO.getToEmail())
                .templateAlias(customerIoProperties.getTemplateAlias().getCustomerFreeGift())
                .templateModel(map)
                .build();
        return this.send(emailSendBO);
    }
}
