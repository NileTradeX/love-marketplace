package com.love.influencer.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfUserOrderRefundBO implements Serializable {
    private Long orderId;
    private List<Item> items;
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item implements Serializable {
        private Long skuId;
        private BigDecimal refundAmount;
    }

}
