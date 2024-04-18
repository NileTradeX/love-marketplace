package com.love.influencer.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.common.param.ByUserIdParam;
import com.love.common.param.IdParam;
import com.love.influencer.backend.model.param.InfStoreChangeCoverParam;
import com.love.influencer.backend.model.param.InfStoreEditParam;
import com.love.influencer.backend.model.param.InfStoreQueryByDisplayNameParam;
import com.love.influencer.backend.model.param.InfStoreSaveParam;
import com.love.influencer.backend.model.vo.GoodsSortTypeVO;
import com.love.influencer.backend.model.vo.InfStoreVO;
import com.love.influencer.bo.InfStoreChangeCoverBO;
import com.love.influencer.bo.InfStoreEditBO;
import com.love.influencer.bo.InfStoreQueryByDisplayNameBO;
import com.love.influencer.bo.InfStoreSaveBO;
import com.love.influencer.client.InfStoreFeignClient;
import com.love.influencer.dto.GoodsSortTypeDTO;
import com.love.influencer.dto.InfStoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfluencerStoreManager {

    private final InfStoreFeignClient infStoreFeignClient;

    public InfStoreVO save(InfStoreSaveParam infStoreSaveParam) {
        InfStoreSaveBO infStoreSaveBO = BeanUtil.copyProperties(infStoreSaveParam, InfStoreSaveBO.class);
        infStoreSaveBO.setInfluencerId(infStoreSaveParam.getUserId());
        InfStoreDTO infStoreDTO = infStoreFeignClient.save(infStoreSaveBO);
        return BeanUtil.copyProperties(infStoreDTO, InfStoreVO.class);
    }

    public InfStoreVO edit(InfStoreEditParam infStoreEditParam) {
        InfStoreEditBO infStoreEditBO = BeanUtil.copyProperties(infStoreEditParam, InfStoreEditBO.class);
        InfStoreDTO infStoreDTO = infStoreFeignClient.edit(infStoreEditBO);
        return BeanUtil.copyProperties(infStoreDTO, InfStoreVO.class);
    }

    public InfStoreVO queryById(ByUserIdParam byUserIdParam) {
        InfStoreDTO influencerStoreDTO = infStoreFeignClient.queryById(IdParam.builder().id(byUserIdParam.getUserId()).build());
        return BeanUtil.copyProperties(influencerStoreDTO, InfStoreVO.class);
    }

    public Boolean changeCover(InfStoreChangeCoverParam infStoreChangeCoverParam) {
        InfStoreChangeCoverBO infStoreChangeCoverBO = BeanUtil.copyProperties(infStoreChangeCoverParam, InfStoreChangeCoverBO.class);
        return infStoreFeignClient.changeCover(infStoreChangeCoverBO);
    }

    public List<GoodsSortTypeVO> queryGoodSortTypes() {
        List<GoodsSortTypeDTO> goodsSortTypes = infStoreFeignClient.queryGoodSortTypes();
        return BeanUtil.copyToList(goodsSortTypes, GoodsSortTypeVO.class);
    }

    public InfStoreVO queryByDisplayName(InfStoreQueryByDisplayNameParam infStoreQueryParam) {
        InfStoreQueryByDisplayNameBO infStoreQueryByDisplayNameBO = BeanUtil.copyProperties(infStoreQueryParam, InfStoreQueryByDisplayNameBO.class);
        InfStoreDTO infStoreDTO = infStoreFeignClient.queryByDisplayName(infStoreQueryByDisplayNameBO);
        return BeanUtil.copyProperties(infStoreDTO, InfStoreVO.class);
    }
}
