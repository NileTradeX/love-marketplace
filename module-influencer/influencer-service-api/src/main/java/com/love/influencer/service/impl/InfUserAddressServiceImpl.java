package com.love.influencer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.influencer.bo.InfStoreQueryByInfIdBO;
import com.love.influencer.bo.InfUserAddressBO;
import com.love.influencer.dto.InfUserAddressDTO;
import com.love.influencer.entity.InfUserAddress;
import com.love.influencer.mapper.InfUserAddressMapper;
import com.love.influencer.service.InfUserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfUserAddressServiceImpl extends ServiceImpl<InfUserAddressMapper, InfUserAddress> implements InfUserAddressService {

    @Override
    public InfUserAddressDTO save(InfUserAddressBO infUserAddressBO) {
        InfUserAddress influencerUserAddress = BeanUtil.copyProperties(infUserAddressBO, InfUserAddress.class);
        this.save(influencerUserAddress);
        return BeanUtil.copyProperties(influencerUserAddress, InfUserAddressDTO.class);
    }

    @Override
    public InfUserAddressDTO edit(InfUserAddressBO infUserAddressBO) {
        InfUserAddress influencerUserAddress = BeanUtil.copyProperties(infUserAddressBO, InfUserAddress.class);
        this.updateById(influencerUserAddress);
        return BeanUtil.copyProperties(influencerUserAddress, InfUserAddressDTO.class);
    }

    @Override
    public List<InfUserAddressDTO> queryByUserId(InfStoreQueryByInfIdBO infStoreQueryByInfIdBO) {
        List<InfUserAddress> userAddresses = this.lambdaQuery().eq(InfUserAddress::getInfluencerId, infStoreQueryByInfIdBO.getInfluencerId()).list();
        return BeanUtil.copyToList(userAddresses, InfUserAddressDTO.class);
    }

    @Override
    public Boolean deleteByInfluencerId(Long influencerId) {
        return this.remove(new LambdaQueryWrapper<InfUserAddress>().eq(InfUserAddress::getInfluencerId, influencerId));
    }
}
