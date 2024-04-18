package com.love.marketplace.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReviewVO implements Serializable {
    private Long userId;
    private String title;
    private String content;
    private Integer rating;
    private LocalDateTime createTime;
    private User user;

    @Data
    public static class User implements Serializable {
        private String firstName;
        private String lastName;
        private String avatar;
    }
}
