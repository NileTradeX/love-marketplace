package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemSaveBO implements Serializable {
    private Long goodsId;
    private String goodsTitle;
    private Long skuId;
    private BigDecimal price;
    private Integer qty;
}
