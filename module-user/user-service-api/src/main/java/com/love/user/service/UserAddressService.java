package com.love.user.service;

import com.love.common.param.IdParam;
import com.love.user.sdk.bo.UserAddressEditBO;
import com.love.user.sdk.bo.UserAddressQueryListBO;
import com.love.user.sdk.bo.UserAddressSaveBO;
import com.love.user.sdk.dto.UserAddressDTO;

import java.util.List;


public interface UserAddressService {

    UserAddressDTO save(UserAddressSaveBO userAddressSaveBO);

    Boolean saveBatch(List<UserAddressSaveBO> userAddressSaveBOS);

    Boolean edit(UserAddressEditBO userAddressEditBO);

    UserAddressDTO queryById(IdParam idParam);

    Boolean deleteById(IdParam idParam);

    List<UserAddressDTO> queryByUserId(UserAddressQueryListBO userAddressQueryListBO);
}
