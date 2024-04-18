package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AfterSalesRecordQueryBO implements Serializable {
    private String orderNo;
    private String merOrderNo;
    private List<AfterSalesRecordQuerySkuBO> querySkuBOList;
}
