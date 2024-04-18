package com.love.marketplace.controller;

import com.love.common.param.ByUserIdParam;
import com.love.common.param.UserIdParam;
import com.love.common.result.Result;
import com.love.marketplace.manager.ShoppingCartManager;
import com.love.marketplace.model.param.ShoppingCartGoodsMergeParam;
import com.love.marketplace.model.param.ShoppingCartGoodsQueryListParam;
import com.love.marketplace.model.param.ShoppingCartGoodsSaveParam;
import com.love.marketplace.model.vo.ShoppingCartVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/cart")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "ShoppingCart", description = "All ShoppingCart operation")
public class ShoppingCartController {

    private final ShoppingCartManager shoppingCartManager;

    @PostMapping("saveGoods")
    @Operation(operationId = "cartSaveGoods")
    public Result<Boolean> saveGoods(@RequestBody @Validated ShoppingCartGoodsSaveParam shoppingCartGoodsSaveParam) {
        return Result.success(shoppingCartManager.saveGoods(shoppingCartGoodsSaveParam));
    }

    @PostMapping("deleteGoods")
    @Operation(operationId = "cartDeleteGoods")
    public Result<Boolean> deleteGoods(@RequestBody ByUserIdParam idParam) {
        return Result.success(shoppingCartManager.deleteGoods(idParam));
    }

    @GetMapping("goodsList")
    @Operation(operationId = "cartGoodsList")
    public Result<ShoppingCartVO> goodsList(ShoppingCartGoodsQueryListParam shoppingCartGoodsQueryListParam) {
        return Result.success(shoppingCartManager.goodsList(shoppingCartGoodsQueryListParam));
    }

    @GetMapping("goodsList/guest")
    @Operation(operationId = "cartGoodsListGuest")
    public Result<ShoppingCartVO> goodsListGuest(ShoppingCartGoodsQueryListParam shoppingCartGoodsQueryListParam) {
        return Result.success(shoppingCartManager.goodsList(shoppingCartGoodsQueryListParam));
    }

    @GetMapping("clear")
    @Operation(operationId = "clearCart")
    public Result<Boolean> goodsListGuest(UserIdParam userIdParam) {
        return Result.success(shoppingCartManager.clear(userIdParam));
    }

    @PostMapping("mergeGoods")
    @Operation(operationId = "cartMergeGoods")
    public Result<Boolean> mergeGoods(@RequestBody @Validated ShoppingCartGoodsMergeParam shoppingCartGoodsMergeParam) {
        return Result.success(shoppingCartManager.mergeGoods(shoppingCartGoodsMergeParam));
    }
}
