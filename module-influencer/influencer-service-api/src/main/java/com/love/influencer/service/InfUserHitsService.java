package com.love.influencer.service;

import com.love.influencer.bo.DashboardQueryBO;
import com.love.influencer.bo.InfUserHitsSaveBO;
import com.love.influencer.dto.InfUserHitsDTO;
import com.love.influencer.entity.InfUserHits;

import java.util.List;

public interface InfUserHitsService {

    InfUserHitsDTO save(InfUserHitsSaveBO infUserHitsSaveBO);

    List<InfUserHits> queryInfluencerById(DashboardQueryBO dashboardQueryBO);
}
