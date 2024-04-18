package com.love.influencer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.influencer.bo.DashboardQueryBO;
import com.love.influencer.bo.InfUserHitsSaveBO;
import com.love.influencer.dto.InfUserHitsDTO;
import com.love.influencer.entity.InfUserHits;
import com.love.influencer.mapper.InfUserHitsMapper;
import com.love.influencer.service.InfUserHitsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfUserHitsServiceImpl extends ServiceImpl<InfUserHitsMapper, InfUserHits> implements InfUserHitsService {

    @Override
    public InfUserHitsDTO save(InfUserHitsSaveBO infUserHitsSaveBO) {
        InfUserHits influencerUserHits = BeanUtil.copyProperties(infUserHitsSaveBO, InfUserHits.class);
        this.save(influencerUserHits);
        return BeanUtil.copyProperties(influencerUserHits, InfUserHitsDTO.class);
    }

    @Override
    public List<InfUserHits> queryInfluencerById(DashboardQueryBO dashboardQueryBO) {
        return this.lambdaQuery().eq(InfUserHits::getInfluencerId, dashboardQueryBO.getUserId()).list();
    }
}
