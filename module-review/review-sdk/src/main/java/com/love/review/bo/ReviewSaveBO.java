package com.love.review.bo;

import com.love.review.enums.ReviewType;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewSaveBO implements Serializable {
    private Long pid;
    private String title;
    private String content;
    private Integer rating;
    private Long userId;
    private Long merchantId;
    private Integer type = ReviewType.GOODS.getType();
    private Long relatedId;
    private String relatedStr;
}
