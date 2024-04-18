package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class GoodsQueryPageBO implements Serializable {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private Long id;
    private String title;
    private Long merchantId;
    private Long firstCateId;
    private Long secondCateId;
    private Integer status;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private LocalDateTime startCreationDate;
    private LocalDateTime endCreationDate;
    private Integer minSalesVolume;
    private Integer maxSalesVolume;
    private Integer type;
}
