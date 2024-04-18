package com.love.backend.manager;


import com.love.backend.model.param.UserAddressSetDefaultParam;
import com.love.common.param.IdParam;
import com.love.user.client.UserAddressFeignClient;
import com.love.user.sdk.bo.UserAddressEditBO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserAddressManager {

    private final UserAddressFeignClient userAddressFeignClient;

    public Boolean delete(IdParam idParam) {
        return userAddressFeignClient.deleteById(idParam);
    }

    public Boolean setDefault(UserAddressSetDefaultParam userAddressSetDefaultParam) {
        UserAddressEditBO userAddressEditBO = new UserAddressEditBO();
        userAddressEditBO.setId(userAddressSetDefaultParam.getId());
        userAddressEditBO.setUserId(userAddressSetDefaultParam.getCustomerId());
        userAddressEditBO.setIsDefault(1);
        return userAddressFeignClient.edit(userAddressEditBO);
    }
}
