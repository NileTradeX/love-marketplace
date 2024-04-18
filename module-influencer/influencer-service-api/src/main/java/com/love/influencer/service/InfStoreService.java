package com.love.influencer.service;

import com.love.common.param.IdParam;
import com.love.influencer.bo.*;
import com.love.influencer.dto.GoodsSortTypeDTO;
import com.love.influencer.dto.InfStoreDTO;
import com.love.influencer.dto.InfStoreIdDTO;
import com.love.influencer.entity.InfStore;

import java.util.List;


public interface InfStoreService {

    InfStore checkDisplayNameExists(CheckDisplayNameExistBO checkDisplayNameExistBO);

    InfStoreDTO save(InfStoreSaveBO infStoreSaveBO);

    InfStoreDTO edit(InfStoreEditBO infStoreEditBO);

    InfStoreDTO queryById(IdParam idParam);

    Boolean deleteByInfluencerId(Long influencerId);

    Boolean changeCover(InfStoreChangeCoverBO infStoreChangeCoverBO);

    List<GoodsSortTypeDTO> queryGoodSortTypes();

    InfStoreDTO queryByDisplayName(InfStoreQueryByDisplayNameBO infStoreQueryByDisplayNameBO);

    InfStoreIdDTO queryStoreIdByInfluencerId(InfStoreQueryByInfIdBO infStoreQueryByInfIdBO);
}
