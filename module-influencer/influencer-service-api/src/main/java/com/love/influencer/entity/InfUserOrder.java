package com.love.influencer.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("inf_user_order")
public class InfUserOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long influencerId;
    private Long orderId;
    private String orderItemNo; 
    private Long buyerId;
    private BigDecimal totalAmount;
    private BigDecimal commission;
    private BigDecimal commissionRate;
    private BigDecimal merCommissionRate;
    private BigDecimal refundAmount;
    private Long goodsId;
    private Long skuId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
