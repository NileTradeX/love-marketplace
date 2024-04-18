package com.love.marketplace.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AfterSalesVO implements Serializable {
    private Long brandId;
    private String brandLogo;
    private String brandName;
    private String afterSaleNo;
    private String orderNo;
    private Long merOrderId;
    private String merOrderNo;
    private Integer afterSaleStatus;
    private String merchantDealDesc;
    private LocalDateTime orderTime;
    private LocalDateTime createTime;
    private List<AfterSalesItemVO> items;
}
