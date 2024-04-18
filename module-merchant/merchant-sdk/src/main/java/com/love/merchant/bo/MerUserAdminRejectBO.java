package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserAdminRejectBO implements Serializable {
    private Long id;
    private String reason;
}
