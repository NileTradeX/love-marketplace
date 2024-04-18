package com.love.influencer.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.influencer.backend.model.param.DashboardParam;
import com.love.influencer.bo.DashboardQueryBO;
import com.love.influencer.client.InfDashboardFeignClient;
import com.love.influencer.dto.DashboardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfluencerDashboardManager {

    private final InfDashboardFeignClient infDashboardFeignClient;

    public DashboardDTO home(DashboardParam dashboardParam) {
        DashboardQueryBO dashboardQueryBO = BeanUtil.copyProperties(dashboardParam, DashboardQueryBO.class);
        return infDashboardFeignClient.dashboard(dashboardQueryBO);
    }

}
