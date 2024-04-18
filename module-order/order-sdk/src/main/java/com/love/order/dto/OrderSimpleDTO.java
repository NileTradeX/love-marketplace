package com.love.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderSimpleDTO implements Serializable {
    private Long id;
    private Long buyerId;
    private String orderNo;
    private Integer status;
    private LocalDateTime createTime;
}
