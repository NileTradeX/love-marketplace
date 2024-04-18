package com.love.shipment.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.com.google.common.collect.Maps;
import com.love.common.exception.BizException;
import com.love.common.result.Result;
import com.love.common.util.HttpUtil;
import com.love.shipment.bo.*;
import com.love.shipment.dto.QueryTracksDTO;
import com.love.shipment.dto.ShippoJwtTokenDTO;
import com.love.shipment.dto.ShippoOauthTokenDTO;
import com.love.shipment.service.ShippoMerchantService;
import com.love.shipment.service.ShippoTrackService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("shipment")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShipmentController {

    private final Logger logger = LoggerFactory.getLogger(ShipmentController.class);

    @Value("${shippo.oauth.client-id}")
    private String oauthClientId;

    @Value("${shippo.oauth.client-secret}")
    private String oauthClientSecret;

    @Value("${shippo.api-token}")
    private String apiToken;


    private final ShippoMerchantService shippoMerchantService;

    private final ShippoTrackService shippoTrackService;

    @GetMapping("oauthToken")
    public Result<Boolean> oauthToken(ShippoOauthTokenBO oauthTokenBO) throws Exception {
        Map<String, Object> params = Maps.newHashMap();
        params.put("client_id", oauthClientId);
        params.put("client_secret", oauthClientSecret);
        params.put("code", oauthTokenBO.getCode());
        params.put("grant_type", "authorization_code");
        String json = HttpUtil.post("https://goshippo.com/oauth/access_token", params);

        logger.warn("===> access_token result : {}", json);

        JSONObject response = JSONObject.parseObject(json);
        String error = response.getString("error");
        if (StringUtils.isNotBlank(error)) {
            return Result.fail(error);
        }

        return Result.success(shippoMerchantService.save(ShippoAccessTokenBO.builder().merchantId(oauthTokenBO.getMerchantId()).accessToken(response.getString("access_token")).build()));
    }

    @GetMapping("jwtToken")
    public Result<ShippoJwtTokenDTO> jwtToken(ShippoOauthQueryByMerchantBO shippoMerchantBO) throws Exception {
        ShippoOauthTokenDTO shippoOauthTokenDTO = shippoMerchantService.queryByMerchantId(ShippoOauthQueryByMerchantBO.builder().merchantId(shippoMerchantBO.getMerchantId()).build());
        if (Objects.isNull(shippoOauthTokenDTO)) {
            return Result.fail("222", "The merchant has not authorized");
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", String.format("Bearer %s", shippoOauthTokenDTO.getAccessToken()));
        headers.put("Content-Type", "application/json");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("scope", "embedded:carriers");

        String json = HttpUtil.post("https://api.goshippo.com/embedded/authz", jsonObject.toJSONString(), headers);
        JSONObject response = JSONObject.parseObject(json);
        String message = response.getString("message");
        if (StringUtils.isNotBlank(message)) {
            return Result.fail(message);
        }
        return Result.success(new ShippoJwtTokenDTO(response.getString("token"), response.getLong("expiresIn")));
    }

    @GetMapping("tracks")
    public Result<QueryTracksDTO> tracks(QueryTracksBO queryTracksBO) throws Exception {
        if (StringUtils.isBlank(queryTracksBO.getCarriers()) || StringUtils.isBlank(queryTracksBO.getTrackingNo())) {
            throw BizException.build("Order tracking info is empty!");
        }

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", String.format("ShippoToken %s", apiToken));
        String json = HttpUtil.get(String.format("https://api.goshippo.com/tracks/%s/%s", queryTracksBO.getCarriers(), queryTracksBO.getTrackingNo()), header);
        return Result.success(QueryTracksDTO.builder().json(json).build());
    }

    @PostMapping("saveTracks")
    public Result<Boolean> saveTracks(@RequestBody ShippoTrackBO shippoTrackBO) {
        if (StringUtils.isBlank(shippoTrackBO.getCarriers()) || StringUtils.isBlank(shippoTrackBO.getTrackingNo())) {
            throw BizException.build("Order tracking info is empty!");
        }
        shippoTrackService.saveTrack(shippoTrackBO);
        return Result.success(Boolean.TRUE);
    }

    @PostMapping("registerHook")
    public Result<Boolean> registerHook(@RequestBody ShippoTrackBO shippoTrackBO) throws Exception {
        logger.info("registerHook {}",shippoTrackBO);
        if (StringUtils.isBlank(shippoTrackBO.getCarriers()) || StringUtils.isBlank(shippoTrackBO.getTrackingNo())) {
            throw BizException.build("registerHook info is empty!");
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", String.format("ShippoToken %s", apiToken));
        Map<String,Object> params=new HashMap<>();
        params.put("carrier", shippoTrackBO.getCarriers());
        params.put("tracking_number", shippoTrackBO.getTrackingNo());
        params.put("metadata", shippoTrackBO.getMerchantOrderId());
        String json = HttpUtil.post("https://api.goshippo.com/tracks",params , headers);
        logger.info("registerHook response {}",json);
        return Result.success(Boolean.TRUE);
    }
}
