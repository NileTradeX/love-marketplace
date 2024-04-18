package com.love.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("o_order_item")
public class OrderItem implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long merchantId;
    private Long merchantOrderId;
    private String orderItemNo;
    private Long goodsId;
    private String goodsTitle;
    private Long skuId;
    private BigDecimal price;
    private Integer qty;
    private Integer status;
    private String carriers;
    private String trackingNo;
    private LocalDateTime deliveryTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
