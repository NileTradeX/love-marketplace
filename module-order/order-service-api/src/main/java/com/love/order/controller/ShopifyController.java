package com.love.order.controller;

import com.love.order.bo.ShopifyOrderCreateBO;
import com.love.common.result.Result;
import com.love.order.service.ShopifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("shopify")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShopifyController {

    private final ShopifyService shopifyService;

    @PostMapping ("order/create")
    public Result<Boolean> shopifyOrderCreate(@RequestBody ShopifyOrderCreateBO shopifyOrderCreateBO) {
        return Result.success(shopifyService.shopifyOrderCreate(shopifyOrderCreateBO));
    }

}
