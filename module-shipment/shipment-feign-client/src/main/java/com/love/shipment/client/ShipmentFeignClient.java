package com.love.shipment.client;

import com.love.shipment.bo.QueryTracksBO;
import com.love.shipment.bo.ShippoOauthQueryByMerchantBO;
import com.love.shipment.bo.ShippoOauthTokenBO;
import com.love.shipment.bo.ShippoTrackBO;
import com.love.shipment.dto.QueryTracksDTO;
import com.love.shipment.dto.ShippoJwtTokenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "shipment-service-api", contextId = "shipmentFeignClient", path = "shipment")
public interface ShipmentFeignClient {

    @GetMapping("oauthToken")
    Boolean oauthToken(@SpringQueryMap ShippoOauthTokenBO oauthTokenBO);

    @GetMapping("jwtToken")
    ShippoJwtTokenDTO jwtToken(@SpringQueryMap ShippoOauthQueryByMerchantBO shippoMerchantBO);

    @GetMapping("tracks")
    QueryTracksDTO tracks(@SpringQueryMap QueryTracksBO queryTracksBO);

    @PostMapping("saveTracks")
    Boolean saveTracks(ShippoTrackBO shippoTrackBO);

    @PostMapping("registerHook")
    Boolean registerHook(ShippoTrackBO shippoTrackBO);
}
