package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyMerchantBO implements Serializable {
    private Long merchantId;
    private String merchantName;
    private Boolean isShopifyMerchant = false;
    private String accessTokenType;
    private String accessTokenValue;
    private String principleName;
}
