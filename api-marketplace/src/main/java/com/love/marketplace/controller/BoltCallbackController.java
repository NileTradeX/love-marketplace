package com.love.marketplace.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.love.common.Constants;
import com.love.common.bo.KeyQueryBO;
import com.love.common.client.KeyValueFeignClient;
import com.love.common.dto.KeyValueDTO;
import com.love.common.exception.BizException;
import com.love.common.util.GsonUtil;
import com.love.common.util.MathUtil;
import com.love.common.util.RedisUtil;
import com.love.influencer.bo.InfUserOrderSaveBO;
import com.love.influencer.client.InfUserOrderFeignClient;
import com.love.marketplace.manager.OrderManager;
import com.love.marketplace.manager.PaymentManager;
import com.love.marketplace.model.dto.*;
import com.love.marketplace.model.param.*;
import com.love.marketplace.utils.IdUtil;
import com.love.merchant.client.CommissionRateFeignClient;
import com.love.order.bo.PromoEligibilityParam;
import com.love.order.bo.QuerySimpleOrderBO;
import com.love.order.client.OrderFeignClient;
import com.love.order.dto.OrderDTO;
import com.love.order.dto.OrderItemDTO;
import com.love.payment.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Tag(name = "BoltCallbackAPI", description = "All Goods brand operation")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BoltCallbackController {
    private final KeyValueFeignClient keyValueFeignClient;
    private final StringRedisTemplate stringRedisTemplate;
    private final OrderFeignClient orderFeignClient;
    private final InfUserOrderFeignClient infUserOrderFeignClient;
    private final CommissionRateFeignClient commissionRateFeignClient;

    private final Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new TypeAdapter<Instant>() {
        @Override
        public void write(JsonWriter out, Instant instant) throws IOException {
            if (instant == null) {
                out.nullValue();
            } else {
                out.value(instant.toEpochMilli());
            }
        }

        @Override
        public Instant read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    long epochMilli = in.nextLong();
                    return Instant.ofEpochMilli(epochMilli);
                default:
                    String text = in.nextString();
                    return Instant.parse(text);
            }
        }
    }).create();

    private final OrderManager orderManager;
    private final PaymentManager paymentManager;

    private final RedisUtil redisUtil;

    @Operation(operationId = "boltCallback")
    @PostMapping(consumes = "application/json", produces = "application/json", value = "bolt/callback")
    public ResponseEntity<String> boltCallBack(@RequestBody String requestJson) {
        log.info("Receive a callback request from bolt. The request body : {}", requestJson);
        if (StringUtils.isBlank(requestJson)) {
            log.error("===> empty data <===");
            BoltEventResponseDTO<BoltEventDTO> responseDTO = new BoltEventResponseDTO<>(null, 4005, "Empty JSON content.");
            return ResponseEntity.status(422).body(gson.toJson(responseDTO));
        }
        JsonElement jsonElement = JsonParser.parseString(requestJson);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Set<String> keys = jsonObject.keySet();
        if (keys.contains("event")) {
            String event = jsonObject.get("event").getAsString();
            log.info("===> receive bolt event : {}", event);
            switch (event) {
                case "discounts.code.apply":
                    return discountsCodeApply(gson.fromJson(jsonElement, new TypeToken<BoltEventRequestDTO<BoltDiscountRequestDTO>>() {
                    }.getType()));
                case "cart.create":
                    return cartCreate(gson.fromJson(jsonElement, new TypeToken<BoltEventRequestDTO<BoltCartCreateRequestDTO>>() {
                    }.getType()));
                case "cart.update":
                    return cartUpdate();
                case "order.create":
                    return orderCreate(gson.fromJson(jsonElement, new TypeToken<BoltEventRequestDTO<BoltOrderCreateRequestDTO>>() {
                    }.getType()));
                case "order.shipping":
                    return orderShipping(gson.fromJson(jsonElement, new TypeToken<BoltEventRequestDTO<BoltShippingRequestDTO>>() {
                    }.getType()));
                case "order.shipping_and_tax":
                    return orderShippingAndTax();
                case "order.tax":
                    return orderTax(gson.fromJson(jsonElement, new TypeToken<BoltEventRequestDTO<BoltTaxRequestDTO>>() {
                    }.getType()));
                case "product.info":
                    return productInfo();
                default:
                    BoltEventResponseDTO<BoltEventDTO> responseDTO = new BoltEventResponseDTO<>(event, 33, "No applicable item found in the request.");
                    return ResponseEntity.status(422).body(gson.toJson(responseDTO));
            }
        } else if (keys.contains("object") && keys.contains("type")) {
            String object = jsonObject.get("object").getAsString();
            if ("transaction".equalsIgnoreCase(object)) {
                String type = jsonObject.get("type").getAsString();
                BoltWebhookRequestDTO webhook = gson.fromJson(jsonElement, BoltWebhookRequestDTO.class);
                switch (type) {
                    case "pending":
                        return pending(webhook);
                    case "payment":
                        return payment(webhook);
                    case "rejected_irreversible":
                    case "failed_payment":
                    case "risk_insights":
                        return failedPayment(webhook);
                    case "capture":
                    case "void":
                    case "auth":
                    case "credit":
                    case "newsletter_subscription":
                    default:
                        break;
                }
            }
        }

        return ResponseEntity.ok().build();
    }

    private ResponseEntity<String> discountsCodeApply(BoltEventRequestDTO<BoltDiscountRequestDTO> request) {
        BoltDiscountResponseDTO noDiscount = new BoltDiscountResponseDTO(request.getData().getDiscountCode());
        BoltEventResponseDTO<BoltDiscountResponseDTO> responseDTO = new BoltEventResponseDTO<>(request.getEvent(), noDiscount);
        String response = gson.toJson(responseDTO);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<String> cartCreate(BoltEventRequestDTO<BoltCartCreateRequestDTO> request) {
        List<BoltItemDTO> items = request.getData().getItems();
        List<ItemsParam> itemsParams = new ArrayList<>(items.size());
        int totalAmount = 0;
        for (BoltItemDTO item : items) {
            String reference = item.getReference();
            if (StringUtils.isBlank(reference) || !reference.contains("_")) {
                return ResponseEntity.status(422).body(gson.toJson(new BoltEventResponseDTO<>(request.getEvent(), 17, "reference must be provided.")));
            }
            String[] goods = reference.split("_");
            ItemsParam itemsParam = new ItemsParam();
            itemsParam.setGoodsId(Long.parseLong(goods[0]));
            itemsParam.setSkuId(Long.parseLong(goods[1]));
            itemsParam.setQty(item.getQuantity());
            itemsParams.add(itemsParam);

            totalAmount = totalAmount + item.getTotalAmount();
        }
        String orderNo = IdUtil.getNextOrderNo();
        BoltOrderTokenDTO boltOrderTokenDTO = new BoltOrderTokenDTO();
        boltOrderTokenDTO.setIsGuest(true);
        boltOrderTokenDTO.setOrderNo(orderNo);
        boltOrderTokenDTO.setItemsParams(itemsParams);
        redisUtil.set("bolt::" + orderNo, boltOrderTokenDTO, 60L * 30);
        BoltCartCreateResponseDTO cartCreate = new BoltCartCreateResponseDTO();
        BoltCartDTO cartDTO = new BoltCartDTO();
        cartDTO.setTotalAmount(totalAmount);
        cartDTO.setItems(request.getData().getItems());
        cartDTO.setDisplayId(orderNo);
        cartDTO.setOrderReference(orderNo);
        cartDTO.setCurrency(request.getData().getCurrency());
        cartCreate.setCart(cartDTO);
        BoltEventResponseDTO<BoltCartCreateResponseDTO> responseDTO = new BoltEventResponseDTO<>(request.getEvent(), cartCreate);
        String response = gson.toJson(responseDTO);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<String> cartUpdate() {
        log.info("The cart.update event is called and no handler performs no action.");
        return ResponseEntity.ok("");
    }

    private ResponseEntity<String> orderCreate(BoltEventRequestDTO<BoltOrderCreateRequestDTO> request) {
        BoltOrderCreateRequestDTO requestData = request.getData();
        BoltOrderCreateRequestDTO.OrderDTO order = requestData.getOrder();
        String orderReference = order.getCart().getOrderReference();
        BoltOrderTokenDTO boltOrderTokenDTO = ( BoltOrderTokenDTO ) redisUtil.get("bolt::" + orderReference);
        if (Objects.isNull(boltOrderTokenDTO)) {
            BoltEventResponseDTO<BoltEventDTO> responseDTO = new BoltEventResponseDTO<>(request.getEvent(), 2001003, "Your session timed out due to inactivity. Please refresh your cart and try again.");
            String response = gson.toJson(responseDTO);
            log.info("Response body for bolt callbacks {}", response);
            return ResponseEntity.status(422).body(response);
        }
        List<BoltOrderCreateRequestDTO.OrderDTO.CartDTO.ItemsDTO> items = requestData.getOrder().getCart().getItems();

        if (!orderCheck(boltOrderTokenDTO, request)) {
            return ResponseEntity.status(422).body(gson.toJson(new BoltEventResponseDTO<>(request.getEvent(), 17, "Free gift verify failed!")));
        }
        List<ItemsParam> itemsParams = new ArrayList<>(items.size());
        for (BoltOrderCreateRequestDTO.OrderDTO.CartDTO.ItemsDTO item : items) {
            String itemReference = item.getReference();
            if (StringUtils.isBlank(itemReference) || !itemReference.contains("_")) {
                return ResponseEntity.status(422).body(gson.toJson(new BoltEventResponseDTO<>(request.getEvent(), 17, "reference must be provided.")));
            }
            String[] goodsIdAndSkuId = itemReference.split("_");
            Long goodsId = Long.valueOf(goodsIdAndSkuId[0]);
            Long skuId = Long.valueOf(goodsIdAndSkuId[1]);
            ItemsParam itemsParam = new ItemsParam();
            itemsParam.setGoodsId(goodsId);
            itemsParam.setSkuId(skuId);
            itemsParam.setUnitPrice(item.getUnitPrice().getAmount());
            itemsParam.setQty(item.getQuantity());
            itemsParams.add(itemsParam);
        }

        UserAddressSaveParam addressSaveParam = new UserAddressSaveParam();
        addressSaveParam.setUserId(boltOrderTokenDTO.getUserId());
        addressSaveParam.setFirstName(order.getCart().getShipments().get(0).getShippingAddress().getFirstName());
        addressSaveParam.setLastName(order.getCart().getShipments().get(0).getShippingAddress().getLastName());
        addressSaveParam.setPhoneNumber(order.getCart().getShipments().get(0).getShippingAddress().getPhone());
        addressSaveParam.setCity(order.getCart().getShipments().get(0).getShippingAddress().getLocality());
        addressSaveParam.setState(order.getCart().getShipments().get(0).getShippingAddress().getRegion());
        addressSaveParam.setZipCode(order.getCart().getShipments().get(0).getShippingAddress().getPostalCode());
        addressSaveParam.setAddress(order.getCart().getShipments().get(0).getShippingAddress().getStreetAddress1());
        addressSaveParam.setCompany(order.getCart().getShipments().get(0).getShippingAddress().getCompany());


        OrderCallbackCreateParam param = new OrderCallbackCreateParam();
        param.setOrderNo(orderReference);
        param.setUserId(boltOrderTokenDTO.getUserId());
        param.setEmail(order.getCart().getBillingAddress().getEmail());
        BigDecimal totalAmount = MathUtil.divide(order.getCart().getTotalAmount().getAmount(), 100);
        param.setTotalAmount(totalAmount);
        param.setAppFee(BigDecimal.ZERO);
        param.setShippingFee(BigDecimal.ZERO);
        param.setTaxFee(BigDecimal.ZERO);
        param.setUserAddressSaveParam(addressSaveParam);
        param.setItems(itemsParams);

        try {
            orderManager.createCallbackOrder(boltOrderTokenDTO.getIsGuest(), param);
        } catch (BizException e) {
            BoltEventResponseDTO<BoltEventDTO> responseDTO = new BoltEventResponseDTO<>(request.getEvent(), Integer.parseInt(e.getCode()), e.getMessage());
            String response = gson.toJson(responseDTO);
            log.info("Response body for bolt callbacks {}", response);
            return ResponseEntity.status(422).body(response);
        }

        BoltOrderCreateResponseDTO orderCreate = new BoltOrderCreateResponseDTO();
        orderCreate.setDisplayId(request.getData().getOrder().getCart().getDisplayId());
        BoltEventResponseDTO<BoltOrderCreateResponseDTO> responseDTO = new BoltEventResponseDTO<>(request.getEvent(), orderCreate);
        String response = gson.toJson(responseDTO);

        return ResponseEntity.ok(response);
    }

    private boolean orderCheck(BoltOrderTokenDTO boltOrderTokenDTO, BoltEventRequestDTO<BoltOrderCreateRequestDTO> request) {
        String[] goodsIdAndSkuId = request.getData().getOrder().getCart().getItems().get(0).getReference().split("_");
        String goodsId = goodsIdAndSkuId[0];
        FreeGiftConfig freeGiftConfig = null;
        try {
            KeyValueDTO config = keyValueFeignClient.queryByKey(KeyQueryBO.builder().key(Constants.KEY_FREE_GIFT_CONFIG).build());
            freeGiftConfig = GsonUtil.json2bean(config.getValue(), FreeGiftConfig.class);
        } catch (Exception e) {
            log.error("Need to configure gift goodsId!", e);
        }
        //promo engine order check
        if (Objects.nonNull(freeGiftConfig) && freeGiftConfig.checkGoodsId(Long.valueOf(goodsId))) {
            log.info("free gift order,request={}", request);
            //User must be logged in
            Long userId = boltOrderTokenDTO.getUserId();
            if (Objects.isNull(userId)) {
                return false;
            }

            if (!freeGiftConfig.checkExchangePeriod()) {
                return false;
            }
            //redis lock,prevent concurrency
            Boolean pass = stringRedisTemplate.opsForValue()
                    .setIfAbsent(Constants.REDIS_KEY_FREE_GIFT_PRE + boltOrderTokenDTO.getUserId(), "1", 5, TimeUnit.SECONDS);
            if (Boolean.FALSE.equals(pass)) {
                return false;
            }
            PromoEligibilityParam promoEligibilityParam = PromoEligibilityParam.builder()
                    .goodsId(freeGiftConfig.getGoodsId())
                    .userId(userId)
                    .amount(freeGiftConfig.getAmount().divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP).setScale(2))
                    .beginTime(freeGiftConfig.getActivityBeginTime())
                    .endTime(freeGiftConfig.getActivityEndTime())
                    .build();

            boolean eligibility = orderFeignClient.checkPromoEligibility(promoEligibilityParam);
            if (!eligibility) {
                return false;
            }
            log.info("check success,userId={}", userId);
        }
        //other order
        return true;
    }

    private ResponseEntity<String> orderShipping(BoltEventRequestDTO<BoltShippingRequestDTO> request) {
        String orderReference = request.getData().getCart().getOrderReference();
        BoltMetadataDTO metadata = request.getData().getCart().getMetadata();
        String key = "bolt::" + orderReference;
        BoltOrderTokenDTO boltOrderTokenDTO = ( BoltOrderTokenDTO ) redisUtil.get(key);
        if (Objects.nonNull(boltOrderTokenDTO) && Objects.nonNull(metadata)) {
            String userId = metadata.getUserId();
            if (!StringUtils.isBlank(userId) && !Objects.equals("0", userId)) {
                boltOrderTokenDTO.setUserId(Long.valueOf(metadata.getUserId()));
                boltOrderTokenDTO.setIsGuest(false);
            }

            if (Objects.nonNull(metadata.getInfluencerCode())) {
                boltOrderTokenDTO.getItemsParams().get(0).setInfluencerCode(metadata.getInfluencerCode());
            }
            redisUtil.set(key, boltOrderTokenDTO, 60L * 30);
        }

        BoltShippingResponseDTO freeShipping = new BoltShippingResponseDTO("Free shipping", 0);
        BoltEventResponseDTO<BoltShippingResponseDTO> responseDTO = new BoltEventResponseDTO<>(request.getEvent(), freeShipping);
        String response = gson.toJson(responseDTO);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<String> orderShippingAndTax() {
        log.info("The order.shipping_and_tax event is called and no handler performs no action.");
        return ResponseEntity.ok("");
    }

    private ResponseEntity<String> orderTax(BoltEventRequestDTO<BoltTaxRequestDTO> request) {
        List<BoltItemDTO> items = request.getData().getCart().getItems();
        BoltTaxResponseDTO taxFree = new BoltTaxResponseDTO(items);
        taxFree.setShippingOption(request.getData().getShippingOption());
        BoltEventResponseDTO<BoltTaxResponseDTO> responseDTO = new BoltEventResponseDTO<>(request.getEvent(), taxFree);
        String response = gson.toJson(responseDTO);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<String> productInfo() {
        log.info("The product.info event is called and no handler performs no action.");
        return ResponseEntity.ok("");
    }

    private ResponseEntity<String> pending(BoltWebhookRequestDTO request) {
        BoltWebhookRequestDTO.DataDTO data = request.getData();
        PayInfoCreateParam payInfoCreateParam = PayInfoCreateParam.builder()
                .orderNo(data.getOrder().getCart().getOrderReference())
                .totalAmount(Long.valueOf(data.getAmount().getAmount()))
                .appFee(0L).channel(1).paymentId(data.getReference()).build();
        paymentManager.createPay(payInfoCreateParam);
        return ResponseEntity.ok(gson.toJson(new BoltWebhookResponseDTO()));
    }

    private ResponseEntity<String> payment(BoltWebhookRequestDTO request) {
        BoltWebhookRequestDTO.DataDTO data = request.getData();
        String orderNo = data.getOrder().getCart().getOrderReference();
        List<Long> skuIds = data.getOrder().getCart().getItems().stream()
                .map(itemsDTO -> itemsDTO.getReference().split("_")[1])
                .map(Long::valueOf)
                .collect(Collectors.toList());

        PaymentResultParam paymentResultParam = new PaymentResultParam();
        paymentResultParam.setOrderNo(orderNo);
        paymentResultParam.setSkuIdList(skuIds);
        paymentResultParam.setType("Card");
        paymentResultParam.setStatus(PaymentStatus.SUCCEEDED.getStatus());
        paymentResultParam.setPayTime(LocalDateTime.ofInstant(data.getDate(), ZoneId.systemDefault()));
        paymentResultParam.setEmail(request.getData().getFromUser().getEmails().get(0).getAddress());
        paymentResultParam.setAmount(request.getData().getAmount().getAmount());
        paymentManager.paymentSuccess(paymentResultParam);

        BoltOrderTokenDTO boltOrderTokenDTO = ( BoltOrderTokenDTO ) redisUtil.get("bolt::" + orderNo);
        if (Objects.isNull(boltOrderTokenDTO)) {
            log.error("===> payment success , but no order info");
        } else {
            List<ItemsParam> tempList = boltOrderTokenDTO.getItemsParams().stream().filter(itemsParam -> Objects.nonNull(itemsParam.getInfluencerCode())).collect(Collectors.toList());
            if (!tempList.isEmpty()) {
                OrderDTO orderDTO = orderFeignClient.queryByOrderNo(QuerySimpleOrderBO.builder().orderNo(orderNo).build());
                List<OrderItemDTO> orderItems = new ArrayList<>();
                orderDTO.getMerchantOrders().forEach(merchantOrder -> orderItems.addAll(merchantOrder.getItems()));
                for (ItemsParam item : tempList) {
                    for (OrderItemDTO orderItem : orderItems) {
                        if (item.getGoodsId().longValue() == orderItem.getGoodsId() && item.getSkuId().longValue() == orderItem.getSkuId()) {
                            InfUserOrderSaveBO infUserOrderSaveBO = new InfUserOrderSaveBO();
                            infUserOrderSaveBO.setOrderId(orderDTO.getId());
                            infUserOrderSaveBO.setOrderItemNo(orderItem.getOrderItemNo());
                            infUserOrderSaveBO.setBuyerId(orderDTO.getBuyerId());
                            infUserOrderSaveBO.setGoodsId(item.getGoodsId());
                            infUserOrderSaveBO.setSkuId(item.getSkuId());
                            BigDecimal merCommissionRate = commissionRateFeignClient.queryCurrent(orderItem.getMerchantId());
                            infUserOrderSaveBO.setMerCommissionRate(merCommissionRate);
                            infUserOrderSaveBO.setTotalAmount(MathUtil.multiply(orderItem.getQty(), orderItem.getPrice()));
                            infUserOrderSaveBO.setInfluencerCode(item.getInfluencerCode());
                            infUserOrderFeignClient.save(infUserOrderSaveBO);
                            log.info("===> save influencer order for = {} ,data= {}", item.getInfluencerCode(), GsonUtil.bean2json(infUserOrderSaveBO));
                        }
                    }
                }
            }
        }

        return ResponseEntity.ok(gson.toJson(new BoltWebhookResponseDTO()));
    }

    private ResponseEntity<String> failedPayment(BoltWebhookRequestDTO request) {
        BoltWebhookRequestDTO.DataDTO data = request.getData();
        String orderNo = data.getOrder().getCart().getOrderReference();
        List<Long> skuIds = data.getOrder().getCart().getItems().stream()
                .map(itemsDTO -> itemsDTO.getReference().split("_")[1])
                .map(Long::valueOf)
                .collect(Collectors.toList());

        PayInfoCreateParam payInfoCreateParam = PayInfoCreateParam.builder()
                .orderNo(orderNo)
                .totalAmount(Long.valueOf(data.getAmount().getAmount()))
                .appFee(0L).channel(1).paymentId(data.getReference()).build();
        paymentManager.createPay(payInfoCreateParam);

        PaymentResultParam paymentResultParam = new PaymentResultParam();
        paymentResultParam.setOrderNo(orderNo);
        paymentResultParam.setSkuIdList(skuIds);
        paymentResultParam.setType("Card");
        paymentResultParam.setStatus(PaymentStatus.FAILED.getStatus());
        paymentResultParam.setPayTime(LocalDateTime.ofInstant(data.getDate(), ZoneId.systemDefault()));
        paymentManager.paymentFiled(paymentResultParam);

        return ResponseEntity.ok(gson.toJson(new BoltWebhookResponseDTO()));
    }

}
