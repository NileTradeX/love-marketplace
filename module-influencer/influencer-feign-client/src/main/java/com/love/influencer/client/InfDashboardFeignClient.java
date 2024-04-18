package com.love.influencer.client;

import com.love.influencer.bo.DashboardQueryBO;
import com.love.influencer.dto.DashboardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "influencer-service-api", contextId = "infDashboardFeignClient", path = "influencer/dashboard")
public interface InfDashboardFeignClient {

    @GetMapping("home")
    DashboardDTO dashboard(@SpringQueryMap DashboardQueryBO dashboardQueryBO);

}
