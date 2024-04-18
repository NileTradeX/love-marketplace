package com.love.order.query;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CountByUserIdAndGoodsIdQuery {
    private Long userId;
    private Long goodsId;
    private BigDecimal amount;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
}
