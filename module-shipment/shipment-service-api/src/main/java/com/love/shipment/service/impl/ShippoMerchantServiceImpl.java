package com.love.shipment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.shipment.bo.ShippoAccessTokenBO;
import com.love.shipment.bo.ShippoOauthQueryByMerchantBO;
import com.love.shipment.dto.ShippoOauthTokenDTO;
import com.love.shipment.entity.ShippoMerchant;
import com.love.shipment.mapper.ShippoMerchantMapper;
import com.love.shipment.service.ShippoMerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShippoMerchantServiceImpl extends ServiceImpl<ShippoMerchantMapper, ShippoMerchant> implements ShippoMerchantService {

    @Override
    @Transactional
    public Boolean save(ShippoAccessTokenBO shippoAccessTokenBO) {
        ShippoMerchant shippoMerchant = BeanUtil.copyProperties(shippoAccessTokenBO, ShippoMerchant.class);
        return saveOrUpdate(shippoMerchant);
    }

    @Override
    public ShippoOauthTokenDTO queryByMerchantId(ShippoOauthQueryByMerchantBO shippoOauthQueryByMerchantBO) {
        return BeanUtil.copyProperties(this.lambdaQuery().eq(ShippoMerchant::getMerchantId, shippoOauthQueryByMerchantBO.getMerchantId()).one(), ShippoOauthTokenDTO.class);
    }
}
