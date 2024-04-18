package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ShippingSettingBO implements Serializable {
    private Boolean standardShipping;
    private BigDecimal standardShippingFee;
    private Boolean express;
    private BigDecimal expressFee;
    private Boolean nextDay;
    private BigDecimal nextDayFee;

    private Integer baseQty;
    private BigDecimal basePrice;
    private Integer incrementalQty;
    private BigDecimal incrementalPrice;

    private Integer expressBaseQty;
    private BigDecimal expressBasePrice;
    private Integer expressIncrementalQty;
    private BigDecimal expressIncrementalPrice;

    private Integer nextDayBaseQty;
    private BigDecimal nextDayBasePrice;
    private Integer nextDayIncrementalQty;
    private BigDecimal nextDayIncrementalPrice;
}
