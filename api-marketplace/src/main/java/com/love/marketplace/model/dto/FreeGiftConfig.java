package com.love.marketplace.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class FreeGiftConfig implements Serializable {
    private String code;
    private Long goodsId;
    private String productUrl;
    private BigDecimal amount;
    //exchange gift
    private LocalDateTime exchangeBeginTime;
    private LocalDateTime exchangeEndTime;
    //send email
    private LocalDateTime activityBeginTime;
    private LocalDateTime activityEndTime;
    private long timestamp;

    public boolean checkGoodsId(Long goodsId) {
        return Objects.equals(this.goodsId, goodsId);
    }

    public boolean checkCode(String code) {
        return Objects.equals(this.code, code);
    }

    public boolean checkExchangePeriod() {
        LocalDateTime now = LocalDateTime.now();
        return Objects.nonNull(exchangeBeginTime) && Objects.nonNull(exchangeEndTime) && (now.isAfter(exchangeBeginTime) && now.isBefore(exchangeEndTime));
    }

    public boolean checkActivityPeriod() {
        LocalDateTime now = LocalDateTime.now();
        return Objects.nonNull(activityBeginTime) && Objects.nonNull(activityEndTime) && (now.isAfter(activityBeginTime) && now.isBefore(activityEndTime));
    }
}
