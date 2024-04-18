package com.love.marketplace.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.love.common.util.RedisUtil;
import com.love.goods.bo.UpdateGoodsSalesVolumeBO;
import com.love.goods.client.GoodsFeignClient;
import com.love.order.bo.CompleteOrderByTrackBO;
import com.love.order.client.OrderFeignClient;
import com.love.order.dto.OrderItemDTO;
import com.love.order.enums.OrderStatus;
import com.love.shipment.bo.ShippoTrackBO;
import com.love.shipment.client.ShipmentFeignClient;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShippoWebhookController {

    private final Logger logger = LoggerFactory.getLogger(ShippoWebhookController.class);

    private final RedisUtil redisUtil;

    private final ShipmentFeignClient shipmentFeignClient;

    private final OrderFeignClient orderFeignClient;
    private final GoodsFeignClient goodsFeignClient;

    @Operation(operationId = "shippoCallback")
    @PostMapping(consumes = "application/json", produces = "application/json", value = "shippo/callback")
    public ResponseEntity<String> shippoCallback(@RequestBody String json) {
        logger.info("shippo callback :{}", json);
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        String event = root.getAsJsonPrimitive("event").getAsString();
        JsonObject data = root.getAsJsonObject("data");
        if (!data.has("tracking_status")) {
            logger.error("Missing state node!");
            return ResponseEntity.ok("[error]");
        }
        String shippoStatus = data.getAsJsonObject("tracking_status").getAsJsonPrimitive("status").getAsString();
        String trackNo = data.getAsJsonPrimitive("tracking_number").getAsString();
        JsonPrimitive carriersNode = data.getAsJsonPrimitive("carrier");
        JsonElement metadata = data.get("metadata");
        String carriers = "shippo";
        if (Objects.nonNull(carriersNode)) {
            carriers = carriersNode.getAsString();
        } else {
            logger.error("carrier is null :{}", trackNo);
        }
        Long merchantOrderId = null;
        if (Objects.nonNull(metadata) && metadata.isJsonPrimitive()) {
            try {
                merchantOrderId = metadata.getAsLong();
            } catch (NumberFormatException e) {
                logger.error("NumberFormatException {}", metadata);
            }
        }
        if ("track_updated".equals(event)) {
            shipmentFeignClient.saveTracks(ShippoTrackBO.builder()
                    .carriers(carriers)
                    .trackingNo(trackNo)
                    .merchantOrderId(merchantOrderId)
                    .trackingInfo(json).build());

            if ("DELIVERED".equals(shippoStatus)) {
                CompleteOrderByTrackBO completeOrderByTrackBO = CompleteOrderByTrackBO.builder()
                        .carriers(carriers)
                        .trackingNo(trackNo)
                        .merchantOrderId(merchantOrderId)
                        .build();
                List<OrderItemDTO> orderItemDTOS = orderFeignClient.queryByTrack(completeOrderByTrackBO);
                if (CollectionUtil.isNotEmpty(orderItemDTOS)) {
                    Integer itemStatus = orderItemDTOS.get(0).getStatus();
                    if (OrderStatus.COMPLETED.getStatus() != itemStatus && OrderStatus.CLOSED.getStatus() != itemStatus) {
                        orderFeignClient.completeOrderByTrack(completeOrderByTrackBO);
                        orderItemDTOS.forEach(item -> goodsFeignClient.modifySalesVolume(UpdateGoodsSalesVolumeBO.builder().id(item.getGoodsId()).qty(item.getQty()).build()));
                        logger.info("shippo DELIVERED :{}", trackNo);
                    }
                }
            }
        } else {
            logger.warn("shippo other event:{} trackNo:{}", event, trackNo);
        }

        return ResponseEntity.ok("[accepted]");
    }
}
