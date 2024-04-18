package com.love.shipment.service;

import com.love.shipment.bo.ShippoAccessTokenBO;
import com.love.shipment.bo.ShippoOauthQueryByMerchantBO;
import com.love.shipment.dto.ShippoOauthTokenDTO;

public interface ShippoMerchantService {

    Boolean save(ShippoAccessTokenBO shippoAccessTokenBO);

    ShippoOauthTokenDTO queryByMerchantId(ShippoOauthQueryByMerchantBO shippoOauthQueryByMerchantBO);
}
