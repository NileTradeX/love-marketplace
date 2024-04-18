package com.love.merchant.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AfterSalesRecordVO implements Serializable {
    private String afterSaleNo;
    private LocalDateTime createTime;
    private String merOrderNo;
    private Long merOrderId;
    private Integer afterSaleStatus;
    private String afterSaleReason;
    private String merchantDealDesc;
    private List<AfterSalesSkuVO> items;
}
