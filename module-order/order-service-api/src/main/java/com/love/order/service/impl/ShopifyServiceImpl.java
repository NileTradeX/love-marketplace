package com.love.order.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.com.google.common.base.Strings;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.love.common.util.HttpUtil;
import com.love.order.bo.ShopifyMerchantBO;
import com.love.order.bo.ShopifyNoticeBO;
import com.love.order.bo.ShopifyOrderCreateBO;
import com.love.order.entity.Shopify;
import com.love.order.mapper.ShopifyMapper;
import com.love.order.bo.ShopifyOrderCreateBO.Order;
import com.love.order.bo.ShopifyOrderCreateBO.OrderItem;
import com.love.order.service.ShopifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShopifyServiceImpl extends ServiceImpl<ShopifyMapper, Shopify> implements ShopifyService {

    @Value("${shopify.enable-update}")
    private boolean shopifyEnable;

    @Value("${shopify.embedded-shopify-name}")
    private String embeddedShopifyName;

    @Value("${shopify.encryption-key-string}")
    private String encryptionKeyString;

    private static final String AES = "AES";
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    @Override
    public boolean shopifyOrderCreate(ShopifyOrderCreateBO shopifyOrderCreateBO){
        if (!shopifyEnable) {
            log.warn("Shopify message sending set to disable.");
            return false;
        }

        String shopifyMerchantName = shopifyOrderCreateBO.getShopifyMerchantName();
        // This is for testing, we hard code the embedded shop name
        if (!Strings.isNullOrEmpty(embeddedShopifyName)) {
            shopifyMerchantName = embeddedShopifyName;
        }

        ShopifyMerchantBO shopifyMerchantBO = checkShopifyMerchantStatus(shopifyMerchantName);
        // Don't push notification to Shopify if the merchant does not come from Shopify.
        if (!shopifyMerchantBO.getIsShopifyMerchant()) {
            log.info("The merchant is not a shopify merchant.");
            return false;
        }

        ShopifyNoticeBO shopifyNoticeBO = orderShopifyConvertor(
                shopifyOrderCreateBO.getOrder(), shopifyOrderCreateBO.getOrderItems());
        boolean orderStatusCheck = postShopifyNotification(shopifyMerchantBO, shopifyNoticeBO);
        if (!orderStatusCheck) {
            log.error("Error occur when updating the order to Shopify.");
            return false;
        }
        log.info("Shopify order create success.");
        return true;
    }

    private boolean postShopifyNotification(ShopifyMerchantBO shopifyMerchantBO, ShopifyNoticeBO shopifyNoticeBO) {
        String targetURL = "https://" + shopifyMerchantBO.getPrincipleName() + "/admin/api/2023-07/orders.json";
        log.info("Shopify notice sending: " + targetURL);

        try {
            String messageJson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()
                    .toJson(shopifyNoticeBO);

            Map<String, String> headerMap = HttpUtil.jsonHeaders();
            String encodeToken = shopifyMerchantBO.getAccessTokenValue();
            headerMap.put("X-Shopify-Access-Token", encodeShopify(encodeToken));
            HttpUtil.post(targetURL, messageJson, headerMap);
            return true;
        } catch (Exception e) {
            log.error("Error during post shopify order: {}", e.getMessage(), e);
            return false;
        }
    }

    private String encodeShopify(String accessToken){
        Key key = new SecretKeySpec(Base64.getDecoder().decode(encryptionKeyString), AES);
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            return new String(c.doFinal(Base64.getDecoder().decode(accessToken)));
        } catch (Exception e) {
            log.error("CryptoConverter.convertToEntityAttribute: Exception!", e);
            return null;
        }
    }

    private ShopifyMerchantBO checkShopifyMerchantStatus(String shopifyMerchantName){
        ShopifyMerchantBO shopifyMerchantBO = new ShopifyMerchantBO();
        shopifyMerchantBO.setMerchantName(shopifyMerchantName);
        Shopify shopifyInfo = this.lambdaQuery().eq(
                Shopify::getPrincipalName,
                shopifyMerchantBO.getMerchantName()+".myshopify.com").one();
        log.info("Check if {} is a Shopify merchant: {}", shopifyMerchantBO.getMerchantName(), shopifyInfo != null);
        if (shopifyInfo == null) {
            return shopifyMerchantBO;
        }
        shopifyMerchantBO.setAccessTokenType(shopifyInfo.getAccessTokenType());
        shopifyMerchantBO.setAccessTokenValue(shopifyInfo.getAccessTokenValue());
        shopifyMerchantBO.setPrincipleName(shopifyInfo.getPrincipalName());
        shopifyMerchantBO.setIsShopifyMerchant(true);
        return shopifyMerchantBO;
    }

    private List<ShopifyNoticeBO.Order.LineItem> convertToShopifyLineItems(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::convertToShopifyLineItem)
                .collect(Collectors.toList());
    }

    private ShopifyNoticeBO.Order.LineItem convertToShopifyLineItem(OrderItem emailOrderItemBO) {
        ShopifyNoticeBO.Order.LineItem shopifyLineItem = new ShopifyNoticeBO.Order.LineItem();
        shopifyLineItem.setTitle(emailOrderItemBO.getGoodsTitle());
        shopifyLineItem.setPrice(Double.parseDouble(emailOrderItemBO.getUnitPrice()));
        shopifyLineItem.setQuantity(Integer.parseInt(emailOrderItemBO.getQuantity()));
        return shopifyLineItem;
    }

    private ShopifyNoticeBO orderShopifyConvertor(Order order,
                                                 List<OrderItem> orderItems){
        ShopifyNoticeBO shopifyNoticeBO = new ShopifyNoticeBO();
        ShopifyNoticeBO.Order ShopifyOrder = new ShopifyNoticeBO.Order();
        try {
            /*
            {
              "userId": 20011,
              "firstName": "XIAOFA",
              "lastName": "TONG",
              "phoneNumber": "+12312312312",
              "city": "Corona",
              "state": "California",
              "zipCode": "92878",
              "address": "567 Penrose Dr"
            }
             */
            JSONObject addrJson = JSONObject.parseObject(order.getConsigneeAddress());
            ShopifyNoticeBO.Order.Address address = ShopifyNoticeBO.Order.Address.builder()
                    .firstName(addrJson.getString("firstName"))
                    .lastName(addrJson.getString("lastName"))
                    .phone(addrJson.getString("phoneNumber"))
                    .address1(addrJson.getString("address"))
                    .city(addrJson.getString("city"))
                    .province(addrJson.getString("state"))
                    .zip(addrJson.getString("zipCode"))
                    .country("USA")
                    .build();
            ShopifyOrder.setPhone(order.getConsigneePhone());
            ShopifyOrder.setCustomer(ShopifyNoticeBO.Order.Customer.builder()
                    .firstName(address.getFirstName())
                    .lastName(address.getLastName())
                    .email(order.getConsigneeEmail())
                    .acceptsMarketing(false)
                    .build());
            ShopifyOrder.setShippingAddress(address);
            ShopifyOrder.setBillingAddress(address);
        } catch (Exception e) {
            log.warn("Wrong Address format", e);
            String[] firstNameAndLastName = order.getConsignee().trim().split("\\s+");
            if (firstNameAndLastName.length < 2) {
                log.warn("Cannot parse first name and last name from {}", order.getConsignee().trim());
                firstNameAndLastName = new String[] {order.getConsignee(), ""};
            }
            ShopifyOrder.setCustomer(ShopifyNoticeBO.Order.Customer.builder()
                    .firstName(firstNameAndLastName[0])
                    .lastName(firstNameAndLastName[1])
                    .email(order.getConsigneeEmail())
                    .acceptsMarketing(false)
                    .build());
            ShopifyOrder.setShippingAddress(ShopifyNoticeBO.Order.Address.builder()
                    .firstName(firstNameAndLastName[0])
                    .lastName(firstNameAndLastName[1])
                    .phone(order.getConsigneePhone())
                    .build());
        }

        List<ShopifyNoticeBO.Order.LineItem> lineItems = convertToShopifyLineItems(orderItems);
        ShopifyOrder.setLineItems(lineItems);
        ShopifyOrder.setTotalTax(String.valueOf(order.getTaxes()));
        shopifyNoticeBO.setOrder(ShopifyOrder);

        return shopifyNoticeBO;
    }
}