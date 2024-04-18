package com.love.order.client;

import com.love.order.bo.ShopifyOrderCreateBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-service-api", contextId = "ShopifyFeignClient", path = "shopify")
public interface ShopifyFeignClient {

    @PostMapping("order/create")
    boolean shopifyOrderCreate(ShopifyOrderCreateBO shopifyOrderCreateBO);
}