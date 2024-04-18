package com.love.marketplace.manager;


import com.love.common.Constants;
import com.love.common.bo.KeyQueryBO;
import com.love.common.client.KeyValueFeignClient;
import com.love.common.dto.KeyValueDTO;
import com.love.common.util.GsonUtil;
import com.love.marketplace.model.dto.FreeGiftConfig;
import com.love.marketplace.model.param.FreeGiftCheckCodeParam;
import com.love.order.bo.PromoEligibilityParam;
import com.love.order.client.OrderFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class FreeGiftManager {

    private final OrderFeignClient orderFeignClient;
    private final KeyValueFeignClient keyValueFeignClient;

    public Boolean verify(FreeGiftCheckCodeParam freeGiftCheckCodeParam) {

        KeyValueDTO config = keyValueFeignClient.queryByKey(KeyQueryBO.builder().key(Constants.KEY_FREE_GIFT_CONFIG).build());
        FreeGiftConfig freeGiftConfig = GsonUtil.json2bean(config.getValue(), FreeGiftConfig.class);

        if (!freeGiftConfig.checkCode(freeGiftCheckCodeParam.getCode())) {
            return Boolean.FALSE;
        }

        if (!freeGiftConfig.checkExchangePeriod()) {
            return Boolean.FALSE;
        }

        //check whether to participate
        PromoEligibilityParam promoEligibilityParam = PromoEligibilityParam.builder()
                .goodsId(freeGiftConfig.getGoodsId())
                .userId(freeGiftCheckCodeParam.getUserId())
                .amount(freeGiftConfig.getAmount().divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP).setScale(2))
                .beginTime(freeGiftConfig.getActivityBeginTime())
                .endTime(freeGiftConfig.getActivityEndTime())
                .build();
        return orderFeignClient.checkPromoEligibility(promoEligibilityParam);
    }

    public FreeGiftConfig checkConfig() {
        KeyValueDTO config = keyValueFeignClient.queryByKey(KeyQueryBO.builder().key(Constants.KEY_FREE_GIFT_CONFIG).build());
        FreeGiftConfig freeGiftConfig = GsonUtil.json2bean(config.getValue(), FreeGiftConfig.class);
        freeGiftConfig.setTimestamp(System.currentTimeMillis());
        return freeGiftConfig;
    }
}
