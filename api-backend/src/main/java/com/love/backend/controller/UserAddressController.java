package com.love.backend.controller;

import com.love.backend.manager.UserAddressManager;
import com.love.backend.model.param.UserAddressSetDefaultParam;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user/address")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "UserAddressApi")
public class UserAddressController {

    private final UserAddressManager userAddressManager;

    @GetMapping("deleteById")
    @Operation(operationId = "deleteAddress")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(userAddressManager.delete(idParam));
    }

    @GetMapping("setDefault")
    @Operation(operationId = "setDefaultAddress")
    public Result<Boolean> setDefault(UserAddressSetDefaultParam userAddressSetDefaultParam) {
        return Result.success(userAddressManager.setDefault(userAddressSetDefaultParam));
    }
}
