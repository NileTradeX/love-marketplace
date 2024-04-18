package com.love.influencer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.influencer.bo.DashboardQueryBO;
import com.love.influencer.entity.InfWithdrawRecord;
import com.love.influencer.mapper.InfWithdrawRecordMapper;
import com.love.influencer.service.InfWithdrawRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfWithDrawRecordServiceImpl extends ServiceImpl<InfWithdrawRecordMapper, InfWithdrawRecord> implements InfWithdrawRecordService {
    @Override
    public List<InfWithdrawRecord> queryInfluencerById(DashboardQueryBO dashboardQueryBO) {
        return this.lambdaQuery().eq(InfWithdrawRecord::getInfluencerId, dashboardQueryBO.getUserId()).list();
    }
}
