package com.love.user.controller;

import com.love.common.param.ByUserIdParam;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.user.sdk.bo.ShoppingCartGoodsMergeBO;
import com.love.user.sdk.bo.ShoppingCartGoodsQueryListBO;
import com.love.user.sdk.bo.ShoppingCartGoodsSaveBO;
import com.love.user.sdk.dto.ShoppingCartGoodsDTO;
import com.love.user.service.ShoppingCartGoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/cart")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShoppingCartController {

    private final ShoppingCartGoodsService shoppingCartGoodsService;

    @PostMapping("saveGoods")
    public Result<Boolean> addGoods(@RequestBody ShoppingCartGoodsSaveBO shoppingCartGoodsSaveBO) {
        return Result.success(shoppingCartGoodsService.saveOrUpdate(shoppingCartGoodsSaveBO));
    }

    @GetMapping("goodsList")
    public Result<List<ShoppingCartGoodsDTO>> goodsList(ShoppingCartGoodsQueryListBO shoppingCartGoodsQueryListBO) {
        return Result.success(shoppingCartGoodsService.queryList(shoppingCartGoodsQueryListBO));
    }

    @PostMapping("deleteGoods")
    public Result<Boolean> deleteGoods(@RequestBody ByUserIdParam idParam) {
        return Result.success(shoppingCartGoodsService.deleteById(idParam));
    }

    @GetMapping("clearByUserId")
    public Result<Boolean> clearByUserId(IdParam idParam) {
        return Result.success(shoppingCartGoodsService.clearByUserId(idParam));
    }
    @PostMapping("mergeGoods")
    public Result<Boolean> mergeGoods(@RequestBody ShoppingCartGoodsMergeBO shoppingCartGoodsMergeBO) {
        return Result.success(shoppingCartGoodsService.mergeGoods(shoppingCartGoodsMergeBO));
    }
}
