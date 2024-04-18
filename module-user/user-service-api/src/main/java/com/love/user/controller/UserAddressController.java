package com.love.user.controller;

import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.user.sdk.bo.UserAddressBatchSaveBO;
import com.love.user.sdk.bo.UserAddressEditBO;
import com.love.user.sdk.bo.UserAddressQueryListBO;
import com.love.user.sdk.bo.UserAddressSaveBO;
import com.love.user.sdk.dto.UserAddressDTO;
import com.love.user.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/address")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserAddressController {

    private final UserAddressService userAddressService;

    @PostMapping("save")
    public Result<UserAddressDTO> save(@RequestBody UserAddressSaveBO userAddressSaveBO) {
        return Result.success(userAddressService.save(userAddressSaveBO));
    }

    @PostMapping("saveBatch")
    public Result<Boolean> saveBatch(@RequestBody UserAddressBatchSaveBO userAddressBatchSaveBO) {
        return Result.success(userAddressService.saveBatch(userAddressBatchSaveBO.getAddressList()));
    }

    @PostMapping("edit")
    public Result<Boolean> edit(@RequestBody UserAddressEditBO userAddressEditBO) {
        return Result.success(userAddressService.edit(userAddressEditBO));
    }

    @GetMapping("queryById")
    public Result<UserAddressDTO> queryById(IdParam idParam) {
        return Result.success(userAddressService.queryById(idParam));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(userAddressService.deleteById(idParam));
    }

    @GetMapping("list")
    public Result<List<UserAddressDTO>> list(UserAddressQueryListBO userAddressQueryListBO) {
        return Result.success(userAddressService.queryByUserId(userAddressQueryListBO));
    }
}
