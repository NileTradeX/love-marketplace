package com.love.marketplace.manager;


import cn.hutool.core.bean.BeanUtil;
import com.love.common.param.IdParam;
import com.love.marketplace.model.param.UserAddressEditParam;
import com.love.marketplace.model.param.UserAddressQueryListParam;
import com.love.marketplace.model.param.UserAddressSaveParam;
import com.love.marketplace.model.param.UserAddressSetDefaultParam;
import com.love.marketplace.model.vo.UserAddressVO;
import com.love.user.client.UserAddressFeignClient;
import com.love.user.sdk.bo.UserAddressEditBO;
import com.love.user.sdk.bo.UserAddressQueryListBO;
import com.love.user.sdk.bo.UserAddressSaveBO;
import com.love.user.sdk.dto.UserAddressDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserAddressManager {

    private final UserAddressFeignClient userAddressFeignClient;

    public UserAddressDTO save(UserAddressSaveParam userAddressSaveParam) {
        UserAddressSaveBO userAddressSaveBO = BeanUtil.copyProperties(userAddressSaveParam, UserAddressSaveBO.class);
        return userAddressFeignClient.save(userAddressSaveBO);
    }

    public Boolean edit(UserAddressEditParam userAddressEditParam) {
        UserAddressEditBO userAddressEditBO = BeanUtil.copyProperties(userAddressEditParam, UserAddressEditBO.class);
        return userAddressFeignClient.edit(userAddressEditBO);
    }

    public List<UserAddressVO> list(UserAddressQueryListParam userAddressQueryListParam) {
        UserAddressQueryListBO addressListBO = BeanUtil.copyProperties(userAddressQueryListParam, UserAddressQueryListBO.class);
        List<UserAddressDTO> addressDTOList = userAddressFeignClient.list(addressListBO);
        return BeanUtil.copyToList(addressDTOList, UserAddressVO.class);
    }

    public Boolean delete(IdParam idParam) {
        return userAddressFeignClient.deleteById(idParam);
    }

    public Boolean setDefault(UserAddressSetDefaultParam userAddressSetDefaultParam) {
        UserAddressEditBO userAddressEditBO = new UserAddressEditBO();
        userAddressEditBO.setId(userAddressSetDefaultParam.getId());
        userAddressEditBO.setUserId(userAddressSetDefaultParam.getUserId());
        userAddressEditBO.setIsDefault(1);
        return userAddressFeignClient.edit(userAddressEditBO);
    }
}
