package com.love.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * (AfterSalesRecord)表实体类
 *
 * @author eric
 * @since 2023-07-11 16:46:15
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AfterSalesRecordDTO implements Serializable {

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

    private LocalDateTime createTime;

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

}

