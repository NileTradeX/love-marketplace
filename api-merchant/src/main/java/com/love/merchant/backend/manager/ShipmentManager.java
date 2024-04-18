package com.love.merchant.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.merchant.backend.model.param.ShippoAccessTokenParam;
import com.love.merchant.backend.model.param.ShippoOauthTokenParam;
import com.love.merchant.backend.model.vo.ShippoJwtTokenVO;
import com.love.shipment.bo.ShippoOauthQueryByMerchantBO;
import com.love.shipment.bo.ShippoOauthTokenBO;
import com.love.shipment.client.ShipmentFeignClient;
import com.love.shipment.dto.ShippoJwtTokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShipmentManager {

    private final ShipmentFeignClient shipmentFeignClient;

    public Boolean oauthToken(ShippoOauthTokenParam oauthTokenParam) {
        ShippoOauthTokenBO oauthTokenBO = BeanUtil.copyProperties(oauthTokenParam, ShippoOauthTokenBO.class);
        oauthTokenBO.setMerchantId(oauthTokenParam.getUserId());
        return shipmentFeignClient.oauthToken(oauthTokenBO);
    }

    public ShippoJwtTokenVO jwtToken(ShippoAccessTokenParam shippoAccessTokenParam) {
        ShippoOauthQueryByMerchantBO shippoMerchantBO = BeanUtil.copyProperties(shippoAccessTokenParam, ShippoOauthQueryByMerchantBO.class);
        shippoMerchantBO.setMerchantId(shippoAccessTokenParam.getUserId());
        ShippoJwtTokenDTO jwtTokenDTO = shipmentFeignClient.jwtToken(shippoMerchantBO);
        return BeanUtil.copyProperties(jwtTokenDTO, ShippoJwtTokenVO.class);
    }
}
