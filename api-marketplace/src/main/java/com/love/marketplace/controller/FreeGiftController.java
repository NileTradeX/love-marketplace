package com.love.marketplace.controller;

import com.love.common.result.Result;
import com.love.marketplace.manager.FreeGiftManager;
import com.love.marketplace.model.dto.FreeGiftConfig;
import com.love.marketplace.model.param.FreeGiftCheckCodeParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gift")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "FreeGift")
public class FreeGiftController {

    private final FreeGiftManager freeGiftManager;

    @PostMapping("verify")
    @Operation(operationId = "verify")
    public Result<Boolean> verify(@RequestBody @Validated FreeGiftCheckCodeParam freeGiftCheckCodeParam) {
        return Result.success(freeGiftManager.verify(freeGiftCheckCodeParam));
    }

    @PostMapping("check")
    @Operation(operationId = "check")
    public Result<FreeGiftConfig> check() {
        return Result.success(freeGiftManager.checkConfig());
    }
}
