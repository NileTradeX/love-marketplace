package com.love.marketplace.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.nacos.shaded.com.google.common.collect.Maps;
import com.github.slugify.Slugify;
import com.love.common.bo.EmailOrderItemBO;
import com.love.common.bo.MerchantRefundEmailSendBO;
import com.love.common.client.EmailSendFeignClient;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.common.util.*;
import com.love.goods.bo.GoodsDetailQueryBO;
import com.love.goods.bo.ModifyGoodsSkuCommittedStockBO;
import com.love.goods.bo.UpdateGoodsSalesVolumeBO;
import com.love.goods.client.BrandFeignClient;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.client.GoodsSkuFeignClient;
import com.love.goods.dto.BrandDTO;
import com.love.goods.dto.GoodsDTO;
import com.love.goods.dto.GoodsSimpleDTO;
import com.love.goods.dto.GoodsSkuDTO;
import com.love.goods.enums.GoodsStatus;
import com.love.goods.enums.SkuStatus;
import com.love.marketplace.config.BoltConfigProperties;
import com.love.marketplace.model.dto.BoltCreateOrderTokenRequestDTO;
import com.love.marketplace.model.dto.BoltCreateOrderTokenResponseDTO;
import com.love.marketplace.model.dto.BoltOrderTokenDTO;
import com.love.marketplace.model.dto.ItemDTO;
import com.love.marketplace.model.param.*;
import com.love.marketplace.model.vo.*;
import com.love.marketplace.utils.IdUtil;
import com.love.merchant.bo.MerUserAdminQueryBO;
import com.love.merchant.client.MerchantUserFeignClient;
import com.love.merchant.dto.MerUserAdminDTO;
import com.love.mq.message.OrderCreateMessage;
import com.love.mq.sender.impl.OrderMessageSender;
import com.love.order.bo.*;
import com.love.order.client.AfterSalesRecordClient;
import com.love.order.client.OrderFeignClient;
import com.love.order.dto.*;
import com.love.order.enums.AfterSaleStatus;
import com.love.order.enums.BuyerType;
import com.love.order.enums.OrderStatus;
import com.love.payment.bo.PaymentQueryByOrderNoBO;
import com.love.payment.client.PaymentFeignClient;
import com.love.payment.dto.PaymentSimpleDTO;
import com.love.review.bo.QueryLatestReviewForOrderItemBO;
import com.love.review.client.ReviewFeignClient;
import com.love.review.dto.ReviewDTO;
import com.love.shipment.bo.QueryTracksBO;
import com.love.shipment.client.ShipmentFeignClient;
import com.love.shipment.dto.QueryTracksDTO;
import com.love.user.client.GuestFeignClient;
import com.love.user.client.UserFeignClient;
import com.love.user.sdk.bo.GuestBO;
import com.love.user.sdk.bo.UserQueryByEmailBO;
import com.love.user.sdk.dto.GuestDTO;
import com.love.user.sdk.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.love.common.Constants.GOODS_URL_PREFIX;
import static com.love.common.Constants.S3_HOST;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderManager {

    private final Slugify slugify = new Slugify().withLowerCase(true).withUnderscoreSeparator(false);

    private final Logger logger = LoggerFactory.getLogger(OrderManager.class);

    private final BrandFeignClient brandFeignClient;
    private final UserFeignClient userFeignClient;
    private final OrderFeignClient orderFeignClient;
    private final GoodsFeignClient goodsFeignClient;
    private final PaymentFeignClient paymentFeignClient;
    private final GoodsSkuFeignClient goodsSkuFeignClient;
    private final ShipmentFeignClient shipmentFeignClient;
    private final GuestFeignClient guestFeignClient;
    private final AfterSalesRecordClient afterSalesRecordClient;
    private final OrderMessageSender orderMessageSender;
    private final MerchantUserFeignClient merchantUserFeignClient;
    private final EmailSendFeignClient emailSendFeignClient;
    private final ReviewFeignClient reviewFeignClient;


    private final BoltConfigProperties boltConfigProperties;

    private final RedisUtil redisUtil;

    @Value("${app.merchant.order-view-url}")
    private String viewRefundUrl;

    private Map<Long, AfterSalesSkuRecordDTO> querySkuAfterSales(String orderNo) {
        List<AfterSalesSkuRecordDTO> afterSalesSkuRecords = afterSalesRecordClient.queryLastAfterSaleSkuList(AfterSalesRecordQueryBO.builder().orderNo(orderNo).build());
        if (Objects.nonNull(afterSalesSkuRecords)) {
            return afterSalesSkuRecords.stream().collect(Collectors.toMap(AfterSalesSkuRecordDTO::getSkuId, Function.identity()));
        }
        return Collections.emptyMap();
    }


    public Pageable<MerchantOrderVO> page(OrderQueryPageParam orderQueryPageParam) {
        UserQueryOrderPageBO userQueryOrderPageBO = BeanUtil.copyProperties(orderQueryPageParam, UserQueryOrderPageBO.class);
        Pageable<MerchantOrderDTO> pageable = orderFeignClient.userPage(userQueryOrderPageBO);
        return PageableUtil.toPage(pageable, MerchantOrderVO.class, (src, dst) -> {
            UserDTO user = userFeignClient.simple(IdParam.builder().id(src.getBuyerId()).build());
            if (Objects.nonNull(user)) {
                dst.setBuyer(MerchantOrderVO.Buyer.builder().id(user.getId()).avatar(user.getAvatar()).name(user.getFullName()).email(user.getEmail()).build());
            }

            Map<Long, AfterSalesSkuRecordDTO> skuAfterSalesMap = querySkuAfterSales(src.getOrderNo());

            Long brandId = src.getBrandId();
            BrandDTO brandDTO = brandFeignClient.detail(IdParam.builder().id(brandId).build());
            MerchantOrderVO.Brand brand = BeanUtil.copyProperties(brandDTO, MerchantOrderVO.Brand.class);
            brand.setSlug(slugify.slugify(brand.getName() + "_" + brand.getId()));
            dst.setBrand(brand);

            boolean canRefund = false;

            List<MerchantOrderVO.OrderItem> items = new ArrayList<>();
            List<OrderItemDTO> orderItemDTOS = src.getItems();
            for (OrderItemDTO orderItemDTO : orderItemDTOS) {
                MerchantOrderVO.OrderItem orderItem = BeanUtil.copyProperties(orderItemDTO, MerchantOrderVO.OrderItem.class);
                GoodsSimpleDTO goodsSimpleDTO = goodsFeignClient.simple(IdParam.builder().id(orderItemDTO.getGoodsId()).build());
                GoodsSkuDTO skuDTO = goodsSkuFeignClient.detail(IdParam.builder().id(orderItem.getSkuId()).build());
                orderItem.setGoodsTitle(orderItemDTO.getGoodsTitle());
                orderItem.setSlug(slugify.slugify(orderItemDTO.getGoodsTitle() + "_" + orderItemDTO.getGoodsId()));
                orderItem.setSkuInfo(skuDTO.getAttrValues());
                orderItem.setSkuImg(ObjectUtil.ifNull(skuDTO.getCover(), goodsSimpleDTO.getWhiteBgImg()));
                orderItem.setTotal(MathUtil.multiply(orderItem.getPrice(), orderItem.getQty()));

                if (skuAfterSalesMap.containsKey(orderItem.getSkuId())) {
                    AfterSalesSkuRecordDTO afterSalesSkuRecordDTO = skuAfterSalesMap.get(orderItem.getSkuId());
                    if (Objects.isNull(afterSalesSkuRecordDTO.getAfterSalesRecordDTO())) {
                        throw BizException.build("query refund error");
                    }
                    int status = afterSalesSkuRecordDTO.getAfterSalesRecordDTO().getAfterSaleStatus();
                    orderItem.setAfterSalesStatus(status);
                    orderItem.setReason(afterSalesSkuRecordDTO.getAfterSalesRecordDTO().getAfterSaleReason());
                    if (status == AfterSaleStatus.CANCELLED.getStatus() || status == AfterSaleStatus.DECLINED.getStatus()) {
                        canRefund = true;
                    }
                } else {
                    canRefund = true;
                }
                items.add(orderItem);

                if (Objects.isNull(dst.getShipment()) || Objects.isNull(dst.getShipment().getCarriers())) {
                    dst.setShipment(MerchantOrderVO.Shipment.builder().carriers(orderItem.getCarriers()).trackingNo(orderItem.getTrackingNo()).build());
                }

                if (orderItem.getStatus() == OrderStatus.PENDING_RECEIPT.getStatus()) {
                    boolean completed = orderFeignClient.checkCompleted(IdParam.builder().id(orderItem.getId()).build());
                    if (completed) {
                        goodsFeignClient.modifySalesVolume(UpdateGoodsSalesVolumeBO.builder().id(orderItem.getGoodsId()).qty(orderItem.getQty()).build());
                    }
                }
            }
            //You can apply for after-sales within 30 days after delivery, regardless of the order status
            LocalDateTime deliveryTime = orderItemDTOS.get(0).getDeliveryTime();
            int status = src.getStatus();
            dst.setCanRefund(canRefund && (deliveryTime == null || deliveryTime.isAfter(LocalDateTimeUtil.now().minusDays(30)))&& (status != OrderStatus.CLOSED.getStatus() && status != OrderStatus.AWAIT_PAYMENT.getStatus()));

            dst.setItems(items);

            PaymentSimpleDTO payment = paymentFeignClient.queryByOrderNo(PaymentQueryByOrderNoBO.builder().orderNo(src.getOrderNo()).build());
            if (Objects.nonNull(payment)) {
                dst.setPayment(MerchantOrderVO.Payment.builder().type(payment.getType()).time(payment.getPayTime()).status(payment.getStatus()).amount(payment.getAmount()).build());
            }

            OrderDTO orderDTO = orderFeignClient.queryByOrderNo(QuerySimpleOrderBO.builder().orderNo(src.getOrderNo()).build());
            dst.setReceiver(MerchantOrderVO.Consignee.builder().name(orderDTO.getConsignee()).address(orderDTO.getConsigneeAddress()).email(orderDTO.getConsigneeEmail()).phone(orderDTO.getConsigneePhone()).build());
        });
    }

    public MerchantOrderVO detail(OrderDetailParam orderDetailParam) {
        OrderDTO orderDTO = orderFeignClient.merchantOrderDetail(BeanUtil.copyProperties(orderDetailParam, OrderQueryDetailBO.class));
        return getMerchantOrderDetail(orderDTO);
    }

    private MerchantOrderVO getMerchantOrderDetail(OrderDTO orderDTO) {
        if (Objects.nonNull(orderDTO)) {
            MerchantOrderDTO merchantOrderDTO = orderDTO.getMerchantOrders().get(0);
            MerchantOrderVO merchantOrderVO = BeanUtil.copyProperties(merchantOrderDTO, MerchantOrderVO.class);

            Map<Long, AfterSalesSkuRecordDTO> skuAfterSalesMap = querySkuAfterSales(merchantOrderVO.getOrderNo());

            merchantOrderVO.setReceiver(MerchantOrderVO.Consignee.builder().name(orderDTO.getConsignee()).phone(orderDTO.getConsigneePhone()).email(orderDTO.getConsigneeEmail()).address(orderDTO.getConsigneeAddress()).build());

            BrandDTO brandDTO = brandFeignClient.detail(IdParam.builder().id(merchantOrderVO.getBrandId()).build());
            MerchantOrderVO.Brand brand = BeanUtil.copyProperties(brandDTO, MerchantOrderVO.Brand.class);
            brand.setSlug(slugify.slugify(brand.getName() + "_" + brand.getId()));
            merchantOrderVO.setBrand(BeanUtil.copyProperties(brandDTO, MerchantOrderVO.Brand.class));

            PaymentSimpleDTO payment = paymentFeignClient.queryByOrderNo(PaymentQueryByOrderNoBO.builder().orderNo(merchantOrderVO.getOrderNo()).build());
            if (Objects.nonNull(payment)) {
                merchantOrderVO.setPayment(MerchantOrderVO.Payment.builder().type(payment.getType()).time(payment.getPayTime()).status(payment.getStatus()).amount(payment.getAmount()).build());
            }

            boolean canRefund = false;

            for (MerchantOrderVO.OrderItem item : merchantOrderVO.getItems()) {
                if (Objects.isNull(merchantOrderVO.getShipment()) || Objects.isNull(merchantOrderVO.getShipment().getCarriers())) {
                    merchantOrderVO.setShipment(MerchantOrderVO.Shipment.builder().carriers(item.getCarriers()).trackingNo(item.getTrackingNo()).build());
                }

                item.setSlug(slugify.slugify(item.getGoodsTitle() + "_" + item.getGoodsId()));
                GoodsSimpleDTO goodsSimpleDTO = goodsFeignClient.simple(IdParam.builder().id(item.getGoodsId()).build());
                GoodsSkuDTO skuDTO = goodsSkuFeignClient.detail(IdParam.builder().id(item.getSkuId()).build());
                item.setSkuInfo(skuDTO.getAttrValues());
                item.setSkuImg(ObjectUtil.ifNull(skuDTO.getCover(), goodsSimpleDTO.getWhiteBgImg()));
                item.setTotal(MathUtil.multiply(item.getQty(), item.getPrice()));

                if (skuAfterSalesMap.containsKey(item.getSkuId())) {
                    AfterSalesSkuRecordDTO afterSalesSkuRecordDTO = skuAfterSalesMap.get(item.getSkuId());
                    int status = afterSalesSkuRecordDTO.getAfterSalesRecordDTO().getAfterSaleStatus();
                    item.setAfterSalesStatus(status);
                    item.setReason(afterSalesSkuRecordDTO.getAfterSalesRecordDTO().getAfterSaleReason());
                    if (status == AfterSaleStatus.CANCELLED.getStatus() || status == AfterSaleStatus.DECLINED.getStatus()) {
                        canRefund = true;
                    }
                } else {
                    canRefund = true;
                }

                ReviewDTO reviewDTO = reviewFeignClient.queryLatestForOrderItem(QueryLatestReviewForOrderItemBO.builder().userId(orderDTO.getBuyerId()).relatedId(item.getGoodsId()).relatedStr(String.valueOf(item.getId())).build());
                if (Objects.nonNull(reviewDTO)) {
                    item.setReview(BeanUtil.copyProperties(reviewDTO, ReviewVO.class));
                }
            }

            int status = merchantOrderVO.getStatus();
            merchantOrderVO.setCanRefund(canRefund && (status == OrderStatus.PENDING_RECEIPT.getStatus() || status == OrderStatus.PENDING_SHIPMENT.getStatus()));

            return merchantOrderVO;
        }
        return null;
    }

    public BoltOrderTokenVO createBoltOrderToken(CreateOrderTokenParam createOrderTokenParam) throws Exception {
        boolean guest = true;
        Long userId = createOrderTokenParam.getUserId();
        if (Objects.nonNull(userId)) {
            UserDTO user = userFeignClient.detail(IdParam.builder().id(userId).build());
            if (Objects.nonNull(user)) {
                guest = false;
            }
        }

        List<ItemsParam> items = createOrderTokenParam.getItems();
        List<BoltCreateOrderTokenRequestDTO.CartDTO.ItemsDTO> itemsDTOS = new ArrayList<>(items.size());
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ItemsParam item : items) {
            Long goodsId = item.getGoodsId();
            Long skuId = item.getSkuId();
            ItemDTO itemDTO = getItem(item);

            BigDecimal unitPrice = MathUtil.multiply(itemDTO.getPrice(), 100);
            BigDecimal subtotal = MathUtil.multiply(unitPrice, item.getQty());
            totalAmount = MathUtil.add(totalAmount, subtotal);

            BoltCreateOrderTokenRequestDTO.CartDTO.ItemsDTO itemsDTO = new BoltCreateOrderTokenRequestDTO.CartDTO.ItemsDTO();
            itemsDTO.setBrand(itemDTO.getBrand().getName());
            itemsDTO.setCategory(itemDTO.getFirstCategory().getName());
            itemsDTO.setDescription(itemDTO.getSubTitle());
            itemsDTO.setDetailsUrl(GOODS_URL_PREFIX + itemDTO.getSlug());
            itemsDTO.setImageUrl(S3_HOST + itemDTO.getWhiteBgImg());
            itemsDTO.setName(itemDTO.getTitle());
            itemsDTO.setQuantity(item.getQty());
            itemsDTO.setReference(goodsId + "_" + skuId);
            itemsDTO.setShipmentType("door_delivery");
            itemsDTO.setSku(skuId.toString());
            itemsDTO.setTotalAmount(subtotal.intValue());
            itemsDTO.setType("physical");
            itemsDTO.setUnitPrice(unitPrice.intValue());
            itemsDTO.setWeight(itemDTO.getShippingWeight().intValue());
            itemsDTO.setWeightUnit(itemDTO.getShippingWeightUnit());
            itemsDTOS.add(itemsDTO);
        }
        BoltCreateOrderTokenRequestDTO.CartDTO cartDTO = new BoltCreateOrderTokenRequestDTO.CartDTO();
        cartDTO.setItems(itemsDTOS);
        cartDTO.setTaxAmount(0);
        cartDTO.setTotalAmount(totalAmount.intValue());
        cartDTO.setCurrency("USD");
        String orderNo = IdUtil.getNextOrderNo();
        cartDTO.setDisplayId(orderNo);
        cartDTO.setOrderDescription(orderNo);
        cartDTO.setOrderReference(orderNo);

        BoltCreateOrderTokenRequestDTO createOrderTokenRequestDTO = new BoltCreateOrderTokenRequestDTO();
        createOrderTokenRequestDTO.setCart(cartDTO);
        createOrderTokenRequestDTO.setChannel("browser");

        Map<String, String> headers = new HashMap<>(1);
        headers.put("X-API-Key", boltConfigProperties.getCheckout().getApiKey());
        String request = GsonUtil.bean2json(createOrderTokenRequestDTO);
        logger.info("Create bolt order token request : {}", request);
        String response = HttpUtil.post(boltConfigProperties.getApiUrl() + "/v1/merchant/orders", request, headers);
        logger.info("Create bolt order token response : {}", response);
        BoltCreateOrderTokenResponseDTO orderToken = GsonUtil.json2bean(response, BoltCreateOrderTokenResponseDTO.class);

        BoltOrderTokenDTO boltOrderTokenDTO = new BoltOrderTokenDTO();
        boltOrderTokenDTO.setOrderToken(orderToken.getToken());
        boltOrderTokenDTO.setIsGuest(guest);
        boltOrderTokenDTO.setOrderNo(orderNo);
        boltOrderTokenDTO.setUserId(userId);
        boltOrderTokenDTO.setItemsParams(items);
        redisUtil.set("bolt::" + orderNo, boltOrderTokenDTO, 60L * 30);
        logger.info("===> set bolt order param : {}", orderNo);
        return new BoltOrderTokenVO(orderToken.getToken(), orderNo);
    }

    public OrderTracksVO tracks(IdParam idParam) {
        OrderTrackInfoDTO order = orderFeignClient.queryTracking(idParam);
        QueryTracksDTO orderTracksDTO = shipmentFeignClient.tracks(QueryTracksBO.builder().trackingNo(order.getTrackingNo()).carriers(order.getCarriers()).build());
        if (Objects.nonNull(orderTracksDTO)) {
            return BeanUtil.copyProperties(orderTracksDTO, OrderTracksVO.class);
        }
        throw BizException.build("This is no tracking info yet.");
    }

    public void createCallbackOrder(boolean isGuest, OrderCallbackCreateParam orderCallbackCreateParam) {
        logger.info("orderCallbackCreateParam:{}", orderCallbackCreateParam);

        Long buyerId = orderCallbackCreateParam.getUserId();
        String buyerName = "";
        boolean guestEmailRegistered = false;
        if (Objects.isNull(buyerId)) {
            UserDTO emailUserDTO = userFeignClient.queryByEmail(
                    UserQueryByEmailBO.builder()
                            .email(orderCallbackCreateParam.getEmail())
                            .silent(true)
                            .build()
            );
            //The email has been registered
            if (null != emailUserDTO) {
                buyerId = emailUserDTO.getId();
                buyerName = emailUserDTO.getFullName();
                guestEmailRegistered = true;
            } else {
                GuestDTO guestDTO = guestFeignClient.saveOrUpdate(GuestBO.builder().email(orderCallbackCreateParam.getEmail()).build());
                buyerId = guestDTO.getId();
            }
        } else {
            UserDTO userDTO = userFeignClient.detail(IdParam.builder().id(orderCallbackCreateParam.getUserId()).build());
            if (Objects.isNull(userDTO)) {
                throw BizException.buildWithCode("1010", "user does not exist");
            }

            buyerName = userDTO.getFullName();
        }

        List<ItemDTO> itemDTOS = orderCallbackCreateParam.getItems().stream().map(this::getItem)
                .collect(Collectors.toList());

        Map<String, MultiItemOrderSaveBO.Brand> brandMap = new HashMap<>();
        Map<Long, Integer> sukQtyMap = new HashMap<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ItemDTO itemDTO : itemDTOS) {
            Long merchantId = itemDTO.getMerchantId();
            Long brandId = itemDTO.getBrand().getId();
            String brandName = itemDTO.getBrand().getName();
            StringJoiner stringJoiner = new StringJoiner("/");
            String key = stringJoiner.add(merchantId.toString()).add(brandId.toString()).add(brandName).toString();
            BigDecimal subtotal = MathUtil.multiply(itemDTO.getPrice(), itemDTO.getQty());
            totalAmount = MathUtil.add(totalAmount, subtotal);
            brandMap.computeIfAbsent(key, k -> {
                MultiItemOrderSaveBO.Brand brand = new MultiItemOrderSaveBO.Brand();
                brand.setMerchantId(merchantId);
                brand.setBrandId(brandId);
                brand.setBrandName(brandName);
                brand.setItems(new ArrayList<>());
                return brand;
            });
            OrderItemSaveBO orderItemSaveBO = new OrderItemSaveBO();
            orderItemSaveBO.setGoodsId(itemDTO.getGoodsId());
            orderItemSaveBO.setGoodsTitle(itemDTO.getTitle());
            orderItemSaveBO.setSkuId(itemDTO.getSkuId());
            orderItemSaveBO.setPrice(itemDTO.getPrice());
            orderItemSaveBO.setQty(itemDTO.getQty());
            brandMap.get(key).getItems().add(orderItemSaveBO);
            sukQtyMap.put(itemDTO.getSkuId(), itemDTO.getQty());
        }

        List<MultiItemOrderSaveBO.Brand> brands = new ArrayList<>(brandMap.values());

        UserAddressSaveParam userAddressSaveParam = orderCallbackCreateParam.getUserAddressSaveParam();

        MultiItemOrderSaveBO multiItemOrderSaveBO = new MultiItemOrderSaveBO();
        multiItemOrderSaveBO.setBuyerId(buyerId);
        multiItemOrderSaveBO.setBuyerName(buyerName);
        multiItemOrderSaveBO.setBuyerType(isGuest && !guestEmailRegistered ? BuyerType.GUEST.getType() : BuyerType.LOVE_ACCOUNT.getType());
        multiItemOrderSaveBO.setOrderNo(orderCallbackCreateParam.getOrderNo());
        multiItemOrderSaveBO.setShippingFee(orderCallbackCreateParam.getShippingFee());
        multiItemOrderSaveBO.setTaxes(orderCallbackCreateParam.getTaxFee());
        multiItemOrderSaveBO.setAppFee(orderCallbackCreateParam.getAppFee());
        multiItemOrderSaveBO.setTotalAmount(totalAmount);
        multiItemOrderSaveBO.setConsignee(userAddressSaveParam.getFirstName() + " " + userAddressSaveParam.getLastName());
        multiItemOrderSaveBO.setConsigneePhone(userAddressSaveParam.getPhoneNumber());
        multiItemOrderSaveBO.setConsigneeEmail(orderCallbackCreateParam.getEmail());
        multiItemOrderSaveBO.setConsigneeAddress(GsonUtil.bean2json(userAddressSaveParam));
        multiItemOrderSaveBO.setBrands(brands);


        boolean updateResult = itemDTOS.stream().allMatch(itemsParam -> goodsSkuFeignClient.modifyCommittedStock(
                ModifyGoodsSkuCommittedStockBO.builder().skuId(itemsParam.getSkuId())
                        .committedStock(itemsParam.getQty())
                        .build()));
        if (!updateResult) {
            logger.error("update stock fail , orderNo = {}", multiItemOrderSaveBO.getOrderNo());
            throw BizException.buildWithCode("2001005", "Insufficient Stock");
        }

        MultiItemOrderDTO order;
        try {
            order = orderFeignClient.create(multiItemOrderSaveBO);
        } catch (Exception e) {
            logger.error("create order meet error", e);
            itemDTOS.forEach(itemDTO -> goodsSkuFeignClient.modifyCommittedStock(ModifyGoodsSkuCommittedStockBO.builder().skuId(itemDTO.getSkuId()).committedStock(itemDTO.getQty() * -1).build()));
            throw BizException.buildWithCode("2001003", "Order creation has failed.");
        }

        orderMessageSender.sendOrderDelayMessage(OrderCreateMessage.builder().orderNo(order.getOrderNo()).sukQtyMap(sukQtyMap).build());
    }

    public MerchantOrderVO detailByEmailAndOrderNo(OrderGuestDetailParam orderDetailParam) {
        UserDTO userDTO = userFeignClient.queryByEmail(UserQueryByEmailBO.builder().email(orderDetailParam.getEmail()).silent(true).build());
        Long userId = null;
        if (Objects.nonNull(userDTO)) {
            userId = userDTO.getId();
        }
        OrderDTO orderDTO = orderFeignClient.queryByOrderNoAndEmail(QueryByEmailAndOrderNoBO.builder()
                .merOrderNo(orderDetailParam.getMerOrderNo()).email(orderDetailParam.getEmail()).userId(userId).build());
        return getMerchantOrderDetail(orderDTO);
    }

    public Boolean refundCreate(RefundCreateParam orderRefundCreateParam) {
        AfterSalesApplyBO orderRefundCreateBO = BeanUtil.copyProperties(orderRefundCreateParam, AfterSalesApplyBO.class);
        orderRefundCreateBO.setBuyerId(orderRefundCreateParam.getUserId());
        List<Long> skuIdList = orderRefundCreateBO.getSkuBOList().stream().map(k -> k.getSkuId()).collect(Collectors.toList());
        Map<Long, String> skuMap = goodsSkuFeignClient.querySkuSpecs(IdsParam.builder().idList(skuIdList).build());
        orderRefundCreateBO.getSkuBOList().forEach(o -> {
            o.setSkuInfo(skuMap.get(o.getSkuId()));
        });
        AfterSalesRecordDTO afterSalesRecordDTO = afterSalesRecordClient.customerStartRefund(orderRefundCreateBO);

        sendRefundCreateEmailNotification(orderRefundCreateBO, afterSalesRecordDTO);

        return true;
    }

    public Boolean refundClose(RefundCloseParam refundCloseParam) {
        AfterSalesCustomerCancelBO afterSalesCustomerCancelBO = BeanUtil.copyProperties(refundCloseParam, AfterSalesCustomerCancelBO.class);
        afterSalesRecordClient.customerCloseRefund(afterSalesCustomerCancelBO);
        return true;
    }

    public ItemDTO getItem(ItemsParam itemsParam) {
        GoodsDTO goods = goodsFeignClient.detail(GoodsDetailQueryBO.builder().id(itemsParam.getGoodsId()).skuStatus(SkuStatus.ENABLE.getStatus()).build());
        if (Objects.isNull(goods)) {
            throw BizException.buildWithCode("2001004", "Goods does not exist.");
        }

        if (goods.getStatus() != GoodsStatus.ON_SALES.getStatus()) {
            throw BizException.buildWithCode("2001004", "Goods is out of stock.");
        }

        Long skuId = itemsParam.getSkuId();
        GoodsSkuDTO goodsSku = goods.getSkus().stream().filter(goodsSkuDTO -> Objects.equals(goodsSkuDTO.getId(), skuId)).findFirst().orElseThrow(
                () -> BizException.buildWithCode("2001004", "Goods sku is not exist."));

        if (goodsSku.getAvailableStock() < itemsParam.getQty()) {
            throw BizException.buildWithCode("2001004", "Insufficient Stock");
        }

        Integer unitPrice = itemsParam.getUnitPrice();
        if (unitPrice != null && goodsSku.getPrice().compareTo(MathUtil.divide(unitPrice, 100)) != 0) {
            throw BizException.buildWithCode("2001004", "Prices are not consistent.");
        }

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setGoodsId(goods.getId());
        itemDTO.setTitle(goods.getTitle());
        itemDTO.setSubTitle(goods.getSubTitle());
        itemDTO.setMerchantId(goods.getMerchantId());
        itemDTO.setFirstCategory(goods.getFirstCategory());
        itemDTO.setSecondCategory(goods.getSecondCategory());
        itemDTO.setBrand(goods.getBrand());
        itemDTO.setSlug(goods.getSlug());
        itemDTO.setWhiteBgImg(goods.getWhiteBgImg());
        itemDTO.setSkuId(goodsSku.getId());
        itemDTO.setPrice(goodsSku.getPrice());
        itemDTO.setShippingWeight(goodsSku.getShippingWeight());
        itemDTO.setShippingWeightUnit(goodsSku.getShippingWeightUnit());
        itemDTO.setQty(itemsParam.getQty());
        return itemDTO;
    }

    public Pageable<AfterSalesVO> afterSalesPage(AfterSalesQueryPageParam afterSalesQueryPageParam) {
        AfterSalesOrderQueryPageBO afterSalesOrderQueryPageBO = BeanUtil.copyProperties(afterSalesQueryPageParam, AfterSalesOrderQueryPageBO.class);
        afterSalesOrderQueryPageBO.setBuyerId(afterSalesQueryPageParam.getUserId());
        Pageable<AfterSalesRecordDetailDTO> pageable = afterSalesRecordClient.afterSalesPage(afterSalesOrderQueryPageBO);

        Map<Long, BrandDTO> brandDTOMap = Maps.newHashMap();
        Map<String, MerchantOrderSimpleDTO> orderTimeMap = Maps.newHashMap();

        if (CollUtil.isNotEmpty(pageable.getRecords())) {
            List<Long> brandIdsList = pageable.getRecords().stream().map(r -> r.getBrandId()).distinct().collect(Collectors.toList());
            brandDTOMap.putAll(brandFeignClient.listByIds(IdsParam.builder().idList(brandIdsList).build()).stream().collect(Collectors.toMap(k -> k.getId(), Function.identity())));
            orderTimeMap.putAll(orderFeignClient.queryMerchantOrderList(QueryMerchantOrderNoListBO.builder()
                            .merchantOrderNoList(pageable.getRecords().stream().map(r -> r.getMerOrderNo()).distinct().collect(Collectors.toList())).build())
                    .stream().collect(Collectors.toMap(MerchantOrderSimpleDTO::getMerOrderNo, Function.identity())));
        }

        Pageable<AfterSalesVO> afterSalesVOPageable = PageableUtil.toPage(pageable, AfterSalesVO.class);

        afterSalesVOPageable.getRecords().forEach(r -> {
            r.getItems().forEach(i -> i.setSlug(i.getGoodsTitle() + "_" + i.getGoodsId()));

            BrandDTO brandDTO = brandDTOMap.get(r.getBrandId());
            if (brandDTO != null) {
                r.setBrandName(brandDTO.getName());
                r.setBrandLogo(brandDTO.getLogo());
            }

            MerchantOrderSimpleDTO merchantOrderSimpleDTO = orderTimeMap.get(r.getMerOrderNo());
            if (merchantOrderSimpleDTO != null) {
                r.setMerOrderId(merchantOrderSimpleDTO.getId());
                r.setOrderTime(merchantOrderSimpleDTO.getCreateTime());
            }
        });

        return afterSalesVOPageable;
    }

    private void sendRefundCreateEmailNotification(AfterSalesApplyBO orderRefundCreateBO, AfterSalesRecordDTO afterSalesRecordDTO) {
        try {
            List<EmailOrderItemBO> emailOrderItemBOList = new ArrayList<>();
            for (AfterSalesApplySkuBO afterSalesApplySkuBO : orderRefundCreateBO.getSkuBOList()) {
                GoodsSimpleDTO goodsSimpleDTO = goodsFeignClient.simple(IdParam.builder().id(afterSalesApplySkuBO.getGoodsId()).build());

                String itemTotalAmount = String.valueOf(afterSalesApplySkuBO.getPrice().multiply(new BigDecimal(afterSalesApplySkuBO.getQty())).setScale(2));

                EmailOrderItemBO orderSummaryEmailItemBO = EmailOrderItemBO.builder()
                        .goodsTitle(goodsSimpleDTO.getTitle())
                        .goodsImage(S3_HOST + goodsSimpleDTO.getWhiteBgImg())
                        .skuId(String.valueOf(afterSalesApplySkuBO.getSkuId()))
                        .skuSpec(afterSalesApplySkuBO.getSkuInfo())
                        .unitPrice(String.valueOf(afterSalesApplySkuBO.getPrice()))
                        .quantity(String.valueOf(afterSalesApplySkuBO.getQty()))
                        .itemTotalAmount(itemTotalAmount)
                        .build();

                emailOrderItemBOList.add(orderSummaryEmailItemBO);
            }

            MerUserAdminDTO merUserAdminDTO = merchantUserFeignClient.queryAdminById(MerUserAdminQueryBO.builder().userId(orderRefundCreateBO.getMerchantId()).simple(false).build());
            MerchantRefundEmailSendBO refundEmailSendBO = MerchantRefundEmailSendBO.builder()
                    .toEmail(merUserAdminDTO.getBusinessInfo().getBizOrderMgmtEmail())
                    .refundRequestNumber(afterSalesRecordDTO.getAfterSaleNo())
                    .refundTime(DateUtil.format(afterSalesRecordDTO.getCreateTime()))
                    .merchantOrderNumber(orderRefundCreateBO.getMerOrderNo())
                    .items(emailOrderItemBOList)
                    .refundTotalAmount(String.valueOf(orderRefundCreateBO.getRefundAmount()))
                    .viewUrl(viewRefundUrl)
                    .build();

            emailSendFeignClient.sendMerchantRefundEmail(refundEmailSendBO);
        } catch (RuntimeException e) {
            logger.error("===> Failed to send refund email to merchant: {}", e.getMessage());
        }
    }


}