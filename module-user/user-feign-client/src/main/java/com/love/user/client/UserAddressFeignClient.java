package com.love.user.client;

import com.love.common.param.IdParam;
import com.love.user.sdk.bo.UserAddressBatchSaveBO;
import com.love.user.sdk.bo.UserAddressEditBO;
import com.love.user.sdk.bo.UserAddressQueryListBO;
import com.love.user.sdk.bo.UserAddressSaveBO;
import com.love.user.sdk.dto.UserAddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "user-service-api", contextId = "userAddressFeignClient", path = "user/address")
public interface UserAddressFeignClient {

    @PostMapping("save")
    UserAddressDTO save(UserAddressSaveBO userAddressSaveBO);

    @PostMapping("saveBatch")
    Boolean saveBatch(UserAddressBatchSaveBO userAddressBatchSaveBO);

    @PostMapping("edit")
    Boolean edit(UserAddressEditBO userAddressEditBO);

    @GetMapping("queryById")
    UserAddressDTO detail(@SpringQueryMap IdParam idParam);

    @GetMapping("deleteById")
    Boolean deleteById(@SpringQueryMap IdParam idParam);

    @GetMapping("list")
    List<UserAddressDTO> list(@SpringQueryMap UserAddressQueryListBO userAddressQueryListBO);

}
