package com.love.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.param.IdParam;
import com.love.user.entity.UserAddress;
import com.love.user.mapper.UserAddressMapper;
import com.love.user.sdk.bo.UserAddressEditBO;
import com.love.user.sdk.bo.UserAddressQueryListBO;
import com.love.user.sdk.bo.UserAddressSaveBO;
import com.love.user.sdk.dto.UserAddressDTO;
import com.love.user.service.UserAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserAddressDTO save(UserAddressSaveBO userAddressSaveBO) {
        UserAddress userAddress = BeanUtil.copyProperties(userAddressSaveBO, UserAddress.class);
        if (userAddress.getIsDefault() > 0) {
            this.lambdaUpdate().set(UserAddress::getIsDefault, 0).eq(UserAddress::getUserId, userAddressSaveBO.getUserId()).eq(UserAddress::getIsDefault, 1).update();
        }
        this.save(userAddress);
        return BeanUtil.copyProperties(userAddress, UserAddressDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveBatch(List<UserAddressSaveBO> userAddressSaveBOList) {
        int count = 0;
        for (UserAddressSaveBO userAddressSaveBO : userAddressSaveBOList) {
            if (userAddressSaveBO.getIsDefault() > 0) {
                count++;
            }
        }

        if (count > 1) {
            throw BizException.build("Default address must be 1");
        }

        if (count == 1) {
            this.lambdaUpdate().set(UserAddress::getIsDefault, 0).eq(UserAddress::getUserId, userAddressSaveBOList.get(0).getUserId()).eq(UserAddress::getIsDefault, 1).update();
        }

        return this.saveOrUpdateBatch(BeanUtil.copyToList(userAddressSaveBOList, UserAddress.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(UserAddressEditBO userAddressEditBO) {
        UserAddress userAddress = BeanUtil.copyProperties(userAddressEditBO, UserAddress.class);
        if (userAddress.getIsDefault() > 0) {
            this.lambdaUpdate().set(UserAddress::getIsDefault, 0).eq(UserAddress::getUserId, userAddressEditBO.getUserId()).eq(UserAddress::getIsDefault, 1).update();
        }
        return this.updateById(userAddress);
    }

    @Override
    public UserAddressDTO queryById(IdParam idParam) {
        return BeanUtil.copyProperties(getById(idParam.getId()), UserAddressDTO.class);
    }

    @Override
    public Boolean deleteById(IdParam idParam) {
        return this.removeById(idParam.getId());
    }

    @SuppressWarnings("all")
    @Override
    public List<UserAddressDTO> queryByUserId(UserAddressQueryListBO userAddressQueryListBO) {
        List<UserAddress> userAddresses = this.lambdaQuery().eq(UserAddress::getUserId, userAddressQueryListBO.getUserId()).orderByDesc(UserAddress::getIsDefault, UserAddress::getUpdateTime).list();
        return BeanUtil.copyToList(userAddresses, UserAddressDTO.class);
    }
}
