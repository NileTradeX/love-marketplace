package com.love.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AfterSalesDTO implements Serializable {
    private Long id;
    private Long merchantId;
    private Long orderId;
    private Integer orderStatus;
    private Integer status;
}
