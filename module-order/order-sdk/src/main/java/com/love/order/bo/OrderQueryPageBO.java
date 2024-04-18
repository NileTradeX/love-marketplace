package com.love.order.bo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderQueryPageBO implements Serializable {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private Long merchantId;
    private String orderNo;
    private String trackingNo;
    private Integer status;
    private Long goodsId;
    private String goodsTitle;
    private Long skuId;
    private String consignee;
    private String consigneePhone;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    /**
     * 1:最近六个月 2:六个月前
     */
    private Integer timeRange;
}
