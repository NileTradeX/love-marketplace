package com.love.influencer.client;

import com.love.influencer.bo.InfUserHitsSaveBO;
import com.love.influencer.dto.InfUserHitsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "influencer-service-api", contextId = "infUserHitsFeignClient", path = "influencer/hits")
public interface InfUserHitsFeignClient {

    @PostMapping("save")
    InfUserHitsDTO save(InfUserHitsSaveBO infUserHitsSaveBO);

}
