package com.love.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * (OrderRefund)表实体类
 *
 * @author makejava
 * @since 2023-07-21 10:56:11
 */

@Data
@Accessors(chain = true)
@TableName("order_refund")
public class OrderRefundEntity implements Serializable {

    @TableId(type = IdType.INPUT)
    private String refundNo;

    private String afterSaleNo;

    private String thirdRefundNo;

    private Long buyerId;

    private String orderNo;

    private String merOrderNo;

    private Long merchantId;

    private BigDecimal refundAmount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private LocalDateTime refundTime;

}

