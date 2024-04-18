package com.love.review.bo;

import com.love.review.enums.ReviewType;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewQueryUserPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private Integer type = ReviewType.GOODS.getType();
    private Long relatedId;
    private String relatedStr;
    private Long userId;
    private Integer from;
}
