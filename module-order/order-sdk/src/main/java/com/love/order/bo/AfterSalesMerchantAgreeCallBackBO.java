package com.love.order.bo;

import com.love.order.enums.RefundStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class AfterSalesMerchantAgreeCallBackBO implements Serializable {
    private String afterSaleNo;

    private String thirdRefundNo;

    private RefundStatus refundStatus;
}

