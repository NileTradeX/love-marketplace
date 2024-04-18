package com.love.marketplace.controller;

import com.love.common.util.GsonUtil;
import com.love.common.util.RedisUtil;
import com.love.marketplace.model.dto.AdyenCallbackData;
import com.love.marketplace.model.param.AdyenSplitFundsParam;
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
public class AdyenWebhookController {

    private final Logger logger = LoggerFactory.getLogger(AdyenWebhookController.class);

    private final RedisUtil redisUtil;

    @Operation(operationId = "adyenCallback")
    @PostMapping(consumes = "application/json", produces = "application/json", value = "adyen/callback")
    public ResponseEntity<String> adyenCallBack(@RequestBody String json) {
        logger.error("=====> adyen data :{}", json);

        AdyenCallbackData data;

        try {
            data = GsonUtil.json2bean(json, AdyenCallbackData.class);
            List<AdyenCallbackData.NotificationItems> notificationItems = data.getNotificationItems();
            for (AdyenCallbackData.NotificationItems items : notificationItems) {
                AdyenCallbackData.NotificationItems.NotificationRequestItem item = items.getNotificationRequestItem();
                if ("CAPTURE".equals(item.getEventCode())) {
                    String orderNo = item.getAdditionalData().getMerchantOrderReference();
                    String adyenKey = "adyen::" + orderNo;
                    if (Objects.isNull(redisUtil.get(adyenKey))) {
                        redisUtil.set(adyenKey, 1, 300);

                        AdyenSplitFundsParam splitFundsParam = AdyenSplitFundsParam.builder()
                                .merchantReference(item.getMerchantReference())
                                .pspReference(item.getOriginalReference())
                                .currency(item.getAmount().getCurrency())
                                .amount(( long ) item.getAmount().getValue())
                                .orderNo(orderNo)
                                .build();

                    }
                } else {
                    logger.error("=====> event not deal: {}", item.getEventCode());
                }
            }
        } catch (Exception exception) {
            logger.error("=====> split meet error:", exception);
            return ResponseEntity.ok("error");
        }

        return ResponseEntity.ok("[accepted]");
    }
}
