package com.love.order.service;

import com.love.order.bo.ShopifyOrderCreateBO;

public interface ShopifyService {
    boolean shopifyOrderCreate(ShopifyOrderCreateBO shopifyOrderCreateBO);
}
