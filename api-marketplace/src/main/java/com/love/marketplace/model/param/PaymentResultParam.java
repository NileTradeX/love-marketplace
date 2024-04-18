package com.love.marketplace.model.param;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PaymentResultParam {
    private String orderNo;
    private List<Long> skuIdList;
    private String type;
    private Integer status;
    private LocalDateTime payTime;
    private String email;
    private Integer amount;
}
