package com.love.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AfterSalesDetailDTO implements Serializable {
    private String afterSaleReason;
    private String afterSaleNo;
    private LocalDateTime createTime;
    private String merOrderNo;
    private Integer afterSaleStatus;
    private List<AfterSalesSkuDTO> afterSalesSkuDTOList;

}
