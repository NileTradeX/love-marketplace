package com.love.merchant.backend.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OrderTrackingInfoParam implements Serializable {

    @NotNull(message = "Merchant order id can't be null")
    private Long merchantOrderId;

    @NotBlank(message = "Carriers can't be blank")
    private String carriers;

    @NotBlank(message = "Tracking number can't be blank")
    private String trackingNo;

    private String shippoTransId;
}
