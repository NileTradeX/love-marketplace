package com.love.common.client;

import com.love.common.bo.KeyQueryBO;
import com.love.common.dto.KeyValueDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "common-service-api", contextId = "keyValueFeignClient", path = "kv")
public interface KeyValueFeignClient {

    @GetMapping("key")
    KeyValueDTO queryByKey(@SpringQueryMap KeyQueryBO keyQueryBO);
}
