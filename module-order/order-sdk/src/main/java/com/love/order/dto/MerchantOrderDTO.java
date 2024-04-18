package com.love.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MerchantOrderDTO implements Serializable {
    private Long id;
    private Long brandId;
    private Long orderId;
    private Long buyerId;
    private Long merchantId;
    private String orderNo;
    private String merOrderNo;
    private BigDecimal totalAmount;
    private Integer status;
    private String reason;
    private LocalDateTime createTime;
    private List<OrderItemDTO> items;
}
