package com.love.merchant.backend.controller;

import com.love.common.result.Result;
import com.love.merchant.backend.manager.ShipmentManager;
import com.love.merchant.backend.model.param.ShippoAccessTokenParam;
import com.love.merchant.backend.model.param.ShippoOauthTokenParam;
import com.love.merchant.backend.model.vo.ShippoJwtTokenVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order/shipment")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "ShipmentApi", description = "All Review operation")
public class ShippoController {

    private final ShipmentManager shipmentManager;

    @GetMapping("oauthToken")
    @Operation(operationId = "saveAccessToken")
    public Result<Boolean> oauthToken(@Validated ShippoOauthTokenParam oauthTokenParam) {
        return Result.success(shipmentManager.oauthToken(oauthTokenParam));
    }

    @GetMapping("jwtToken")
    @Operation(operationId = "queryJwtToken")
    public Result<ShippoJwtTokenVO> jwtToken(@Validated ShippoAccessTokenParam shippoAccessTokenParam) {
        return Result.success(shipmentManager.jwtToken(shippoAccessTokenParam));
    }
}
