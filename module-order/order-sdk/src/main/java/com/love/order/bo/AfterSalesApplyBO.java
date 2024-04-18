package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
public class AfterSalesApplyBO implements Serializable {

    private String orderNo;
    private String merOrderNo;
    private Long merchantId;
    private Long brandId;
    private Long buyerId;
    private BigDecimal saleAmount;
    //1redund money only
    private Integer afterSaleType;

    private String afterSaleReason;

    private BigDecimal refundAmount;

    private BigDecimal shippingFee;

    List<AfterSalesApplySkuBO> skuBOList;
}

