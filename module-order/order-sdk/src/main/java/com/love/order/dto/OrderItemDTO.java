package com.love.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderItemDTO implements Serializable {
    private Long id;
    private Long goodsId;
    private String goodsTitle;
    private Long skuId;
    private BigDecimal price;
    private Integer qty;
    private Integer status;
    private String carriers;
    private String trackingNo;
    private String orderItemNo;
    private Long merchantId;
    private LocalDateTime deliveryTime;
    private LocalDateTime createTime;
}
