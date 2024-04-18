package com.love.influencer.service;

import com.love.influencer.bo.DashboardQueryBO;
import com.love.influencer.entity.InfWithdrawRecord;

import java.util.List;

public interface InfWithdrawRecordService {
        List<InfWithdrawRecord> queryInfluencerById(DashboardQueryBO dashboardQueryBO);
}
