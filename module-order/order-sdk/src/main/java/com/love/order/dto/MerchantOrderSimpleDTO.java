package com.love.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MerchantOrderSimpleDTO implements Serializable {
    private Long id;
    private Long brandId;
    private Long orderId;
    private Long merchantId;
    private String orderNo;
    private String merOrderNo;
    private BigDecimal totalAmount;
    private Integer status;
    private LocalDateTime createTime;
}
