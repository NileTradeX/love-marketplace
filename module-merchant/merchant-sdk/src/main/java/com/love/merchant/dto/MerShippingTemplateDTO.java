package com.love.merchant.dto;

import com.love.merchant.bo.ShippingSettingBO;
import lombok.Data;

import java.io.Serializable;


@Data
public class MerShippingTemplateDTO implements Serializable {
    private Long id;
    private Long merchantId;
    private Integer shippingModels;
    private ShippingSettingBO setting;
}
