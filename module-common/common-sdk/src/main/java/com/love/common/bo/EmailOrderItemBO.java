package com.love.common.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailOrderItemBO implements Serializable {
    private String goodsTitle;
    private String goodsImage;
    private String skuId;
    private String skuSpec;
    private String unitPrice;
    private String quantity;
    private String itemTotalAmount;
}

