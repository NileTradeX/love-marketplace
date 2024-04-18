package com.love.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserAdminReviewStatVO implements Serializable {
    private long awaiting;
    private long approved;
    private long rejected;
}
