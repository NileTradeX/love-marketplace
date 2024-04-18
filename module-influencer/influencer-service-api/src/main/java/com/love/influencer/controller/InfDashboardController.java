package com.love.influencer.controller;

import com.love.common.result.Result;
import com.love.influencer.bo.DashboardQueryBO;
import com.love.influencer.dto.DashboardDTO;
import com.love.influencer.service.InfUserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("influencer/dashboard")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfDashboardController {

    private final InfUserOrderService infUserOrderService;

    @GetMapping("home")
    public Result<DashboardDTO> dashboard(DashboardQueryBO dashboardQueryBO) {
        return Result.success(infUserOrderService.dashboard(dashboardQueryBO));
    }
}
