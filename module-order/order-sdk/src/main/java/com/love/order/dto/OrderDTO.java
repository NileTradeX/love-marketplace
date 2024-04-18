package com.love.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO implements Serializable {
    private Long id;
    private String orderNo;
    private Integer buyerType;
    private Long buyerId;
    private String buyerName;
    private BigDecimal taxes;
    private BigDecimal shippingFee;
    private BigDecimal appFee;
    private BigDecimal totalAmount;
    private Integer status;
    private String consignee;
    private String consigneePhone;
    private String consigneeEmail;
    private String consigneeAddress;
    private LocalDateTime createTime;
    private String closeReason;
    private List<MerchantOrderDTO> merchantOrders;
}
