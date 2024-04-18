package com.love.review.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryLatestReviewForOrderItemBO implements Serializable {
    private Long userId;
    private Long relatedId;
    private String relatedStr;
}
