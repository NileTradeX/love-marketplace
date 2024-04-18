package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author eric
 * @since 2023-07-11 16:46:15
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AfterSalesRecordQuerySkuBO implements Serializable {
    private Long goodsId;

    private Long skuId;
}

