package com.love.merchant.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserAdminReviewStatDTO implements Serializable {
    private long awaiting;
    private long approved;
    private long rejected;
}
