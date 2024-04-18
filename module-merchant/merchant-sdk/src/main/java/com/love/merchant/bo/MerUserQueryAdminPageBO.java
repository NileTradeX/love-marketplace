package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MerUserQueryAdminPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private Long id;
    private String account;
    private Integer status;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String sort;
}
