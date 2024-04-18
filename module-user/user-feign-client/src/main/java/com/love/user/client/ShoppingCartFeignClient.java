package com.love.user.client;


import com.love.common.param.ByUserIdParam;
import com.love.common.param.IdParam;
import com.love.user.sdk.bo.ShoppingCartGoodsMergeBO;
import com.love.user.sdk.bo.ShoppingCartGoodsQueryListBO;
import com.love.user.sdk.bo.ShoppingCartGoodsSaveBO;
import com.love.user.sdk.dto.ShoppingCartGoodsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service-api", contextId = "shoppingCartFeignClient", path = "user/cart")
public interface ShoppingCartFeignClient {

    @PostMapping("saveGoods")
    Boolean saveGoods(ShoppingCartGoodsSaveBO shoppingCartGoodsSaveBO);

    @GetMapping("goodsList")
    List<ShoppingCartGoodsDTO> goodsList(@SpringQueryMap ShoppingCartGoodsQueryListBO shoppingCartGoodsQueryListBO);

    @PostMapping("deleteGoods")
    Boolean deleteGoods(ByUserIdParam idParam);

    @GetMapping("clearByUserId")
    Boolean clearByUserId(@SpringQueryMap IdParam idParam);

    @PostMapping("mergeGoods")
    Boolean mergeGoods(@RequestBody ShoppingCartGoodsMergeBO shoppingCartGoodsMergeBO);
}
