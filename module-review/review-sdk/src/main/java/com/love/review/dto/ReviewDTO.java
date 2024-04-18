package com.love.review.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReviewDTO implements Serializable {
    private Long id;
    private String title;
    private String content;
    private Integer rating;
    private Long userId;
    private Long merchantId;
    private Integer type;
    private Long relatedId;
    private String relatedStr;
    private Integer auditStatus;
    private String auditComment;
    private LocalDateTime auditTime;
    private LocalDateTime createTime;
}
