package com.love.review.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewQueryAuditPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private Integer status;
}
