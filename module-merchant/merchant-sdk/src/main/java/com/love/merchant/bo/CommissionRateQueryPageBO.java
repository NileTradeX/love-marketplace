package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CommissionRateQueryPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private String bizName;
    private BigDecimal minRate;
    private BigDecimal maxRate;
    private List<Long> approvedList;
}
