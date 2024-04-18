package com.love.influencer.client;

import com.love.common.param.IdParam;
import com.love.influencer.bo.*;
import com.love.influencer.dto.GoodsSortTypeDTO;
import com.love.influencer.dto.InfStoreDTO;
import com.love.influencer.dto.InfStoreIdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "influencer-service-api", contextId = "infStoreFeignClient", path = "influencer/store")
public interface InfStoreFeignClient {

    @PostMapping("save")
    InfStoreDTO save(InfStoreSaveBO infStoreSaveBO);

    @PostMapping("edit")
    InfStoreDTO edit(InfStoreEditBO infStoreEditBO);

    @GetMapping("queryById")
    InfStoreDTO queryById(@SpringQueryMap IdParam idParam);

    @PostMapping("changeCover")
    Boolean changeCover(InfStoreChangeCoverBO infStoreChangeCoverBO);

    @GetMapping("queryGoodSortTypes")
    List<GoodsSortTypeDTO> queryGoodSortTypes();

    @GetMapping("queryByDisplayName")
    InfStoreDTO queryByDisplayName(@SpringQueryMap InfStoreQueryByDisplayNameBO infStoreQueryByDisplayNameBO);

    @GetMapping("queryStoreIdByInfluencerId")
    InfStoreIdDTO queryStoreIdByInfluencerId(@SpringQueryMap InfStoreQueryByInfIdBO infStoreQueryByInfIdBO);
}
