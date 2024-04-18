package com.love.user.client;

import com.love.common.param.IdsParam;
import com.love.user.sdk.bo.GuestBO;
import com.love.user.sdk.dto.GuestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "user-service-api", contextId = "guestFeignClient", path = "guest")
public interface GuestFeignClient {
    @PostMapping("saveOrUpdate")
    GuestDTO saveOrUpdate(GuestBO guestBO);

    @GetMapping("queryByEmail")
    GuestDTO queryByEmail(@SpringQueryMap GuestBO guestBO);

    @PostMapping("queryByIdList")
    List<GuestDTO> queryByIdList(IdsParam idsParam);
}
