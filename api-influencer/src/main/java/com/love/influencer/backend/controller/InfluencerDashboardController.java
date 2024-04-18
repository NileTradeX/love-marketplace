package com.love.influencer.backend.controller;

import com.love.common.result.Result;
import com.love.influencer.backend.manager.InfluencerDashboardManager;
import com.love.influencer.backend.model.param.DashboardParam;
import com.love.influencer.dto.DashboardDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("influencer/dashboard")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "InfluencerDashboardApi", description = "all Influencer Dashboard manage operation")
public class InfluencerDashboardController {

    private final InfluencerDashboardManager influencerDashboardManager;

    @GetMapping("home")
    public Result<DashboardDTO> dashboard(DashboardParam dashboardParam) {
        return Result.success(influencerDashboardManager.home(dashboardParam));
    }
}
