package com.love.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * (AfterSalesSku)表实体类
 *
 * @author makejava
 * @since 2023-07-21 10:55:55
 */

@Data
@Accessors(chain = true)
@TableName("order_after_sales_sku")
public class AfterSalesSku implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long merchantId;

    private String orderNo;

    private Long buyerId;

    private String afterSaleNo;

    private Long goodsId;

    private Long skuId;

    private BigDecimal price;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private String merOrderNo;

    private String goodsTitle;

    private BigDecimal refundAmount;

    private Integer qty;

    private String skuImg;

    private String skuInfo;

}

