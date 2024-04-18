package com.love.marketplace.controller;

import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.marketplace.manager.UserAddressManager;
import com.love.marketplace.model.param.UserAddressEditParam;
import com.love.marketplace.model.param.UserAddressQueryListParam;
import com.love.marketplace.model.param.UserAddressSaveParam;
import com.love.marketplace.model.param.UserAddressSetDefaultParam;
import com.love.marketplace.model.vo.UserAddressVO;
import com.love.user.sdk.dto.UserAddressDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("user/address")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "UserAddressApi")
public class UserAddressController {

    private final UserAddressManager userAddressManager;

    @PostMapping("save")
    @Operation(operationId = "saveAddress")
    public Result<UserAddressDTO> save(@RequestBody @Validated UserAddressSaveParam userAddressSaveParam) {
        return Result.success(userAddressManager.save(userAddressSaveParam));
    }


    @PostMapping("edit")
    @Operation(operationId = "editAddress")
    public Result<Boolean> edit(@RequestBody @Validated UserAddressEditParam userAddressEditParam) {
        return Result.success(userAddressManager.edit(userAddressEditParam));
    }


    @GetMapping("list")
    @Operation(operationId = "queryAddressList")
    public Result<List<UserAddressVO>> list(@Validated UserAddressQueryListParam userAddressQueryListParam) {
        return Result.success(userAddressManager.list(userAddressQueryListParam));
    }


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
