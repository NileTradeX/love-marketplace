package com.love.order.bo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AfterSalesOrderQueryPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private Long buyerId;
    private Long merchantId;
    private String merOrderNo;
    private String afterSaleNo;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
