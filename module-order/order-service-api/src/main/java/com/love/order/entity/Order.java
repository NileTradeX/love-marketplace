package com.love.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("o_order")
public class Order implements Serializable {
    @TableId(type = IdType.AUTO)
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
