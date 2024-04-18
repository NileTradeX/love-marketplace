package com.love.review.bo;

import com.love.review.enums.ReviewType;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewQueryMerchantPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private Long merchantId;
    private Integer type = ReviewType.GOODS.getType();
    private Long relatedId;
}


