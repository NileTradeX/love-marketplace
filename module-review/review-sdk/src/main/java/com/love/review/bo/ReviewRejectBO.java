package com.love.review.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewRejectBO implements Serializable {
    private Long id;
    private String comment;
}
