package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromoEligibilityParam implements Serializable {

    private Long userId;
    private Long goodsId;
    private BigDecimal amount;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;

}
