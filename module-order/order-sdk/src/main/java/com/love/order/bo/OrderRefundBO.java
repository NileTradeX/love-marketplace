package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (OrderRefund)表实体类
 *
 * @author eric
 * @since 2023-07-11 16:59:23
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderRefundBO implements Serializable {
    private String refundNo;

    private String afterSaleNo;

    private String thirdRefundNo;

    private Long buyerId;

    private String orderNo;

    private String merOrderNo;

    private Long merchantId;

    private BigDecimal refundAmount;
}

