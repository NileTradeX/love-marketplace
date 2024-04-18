package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;


@Data
public class MerShippingTemplateBO implements Serializable {
    private Long merchantId;
    private Integer shippingModels;
    private ShippingSettingBO setting;
}
