package com.love.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("o_merchant_order")
public class MerchantOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long brandId;
    private Long buyerId;
    private Integer buyerType;
    private Long orderId;
    private Long merchantId;
    private String orderNo;
    private String merOrderNo;
    private BigDecimal totalAmount;
    private Integer status;
    private String reason;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
