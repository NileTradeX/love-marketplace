package com.love.influencer.service;

import com.love.influencer.bo.InfStoreQueryByInfIdBO;
import com.love.influencer.bo.InfUserAddressBO;
import com.love.influencer.dto.InfUserAddressDTO;

import java.util.List;


public interface InfUserAddressService {

    InfUserAddressDTO save(InfUserAddressBO infUserAddressBO);

    InfUserAddressDTO edit(InfUserAddressBO infUserAddressBO);

    List<InfUserAddressDTO> queryByUserId(InfStoreQueryByInfIdBO infStoreQueryByInfIdBO);

    Boolean deleteByInfluencerId(Long influencerId);
}
