package com.love.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * (AfterSalesRecord)表实体类
 *
 * @author makejava
 * @since 2023-07-21 10:55:10
 */

@Data
@Accessors(chain = true)
@TableName("order_after_sales_record")
public class AfterSalesRecord implements Serializable {

    @TableId(type = IdType.INPUT)
    private String afterSaleNo;

    private Long buyerId;
    //Not include express fee
    private BigDecimal saleAmount;
    //Include express fee
    private BigDecimal payAmount;
    //1redund money
    private Integer afterSaleType;

    private String afterSaleReason;

    private BigDecimal shippingFee;
    //1.apply: to merchant deal 2.merchant agree 3.complete refund 4.merchant reject 5.cancle
    private Integer afterSaleState;

    private String refundNo;

    private BigDecimal refundAmount;
    //0not refund 1refunded 2close
    private Integer refundStatus;
    //1.Refund requested 2.Refunded 3.Cancelled 4.Rejected
    private Integer afterSaleStatus;

    private String afterSaleRemark;

    private Long merchantId;

    private String orderNo;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private String merOrderNo;
    //0.prepare deal 1.dealed
    private Integer consumerDealStatus;
    //0.not deal 1.cancle
    private Integer consumerDealResult;

    private LocalDateTime consumerDealTime;
    //0.prepare deal 1.dealed
    private Integer merchantDealStatus;
    //0.prepare deal 1.agree 2.reject
    private Integer merchantDealResult;

    private LocalDateTime merchantDealTime;

    private Long brandId;

    private String merchantDealDesc;

}

