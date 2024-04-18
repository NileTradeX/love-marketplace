package com.love.influencer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.param.IdParam;
import com.love.influencer.bo.*;
import com.love.influencer.dto.GoodsSortTypeDTO;
import com.love.influencer.dto.InfStoreDTO;
import com.love.influencer.dto.InfStoreIdDTO;
import com.love.influencer.dto.InfUserInfoDTO;
import com.love.influencer.entity.InfStore;
import com.love.influencer.enums.GoodsSortType;
import com.love.influencer.mapper.InfStoreMapper;
import com.love.influencer.service.InfStoreService;
import com.love.influencer.service.InfUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfStoreServiceImpl extends ServiceImpl<InfStoreMapper, InfStore> implements InfStoreService {

    private final static String storeCover = "res/influencer-store-def-img.jpeg";

    @Autowired
    private InfUserService infUserService;

    @Override
    public InfStore checkDisplayNameExists(CheckDisplayNameExistBO checkDisplayNameExistBO) {
        return this.lambdaQuery().ne(InfStore::getInfluencerId, checkDisplayNameExistBO.getInfluencerId()).apply("lower(display_name) ='" + checkDisplayNameExistBO.getDisplayName().toLowerCase() + "'").one();
    }

    @Override
    public InfStoreDTO save(InfStoreSaveBO infStoreSaveBO) {
        String displayName = infStoreSaveBO.getDisplayName();
        if (!displayName.matches("[a-zA-Z0-9 ]+")) {
            throw BizException.build("displayName format is wrong!");
        }

        InfStore temp = this.lambdaQuery().apply("lower(display_name) ='" + displayName.toLowerCase() + "'").one();
        if (Objects.nonNull(temp)) {
            throw BizException.build("Display Name already exists");
        }
        InfStore infStore = BeanUtil.copyProperties(infStoreSaveBO, InfStore.class);
        if (Objects.isNull(infStore.getCover())) {
            infStore.setCover(storeCover);
        }
        this.save(infStore);
        return BeanUtil.copyProperties(infStore, InfStoreDTO.class);
    }

    @Override
    public InfStoreDTO edit(InfStoreEditBO infStoreEditBO) {
        if (!infStoreEditBO.getDisplayName().matches("[a-zA-Z0-9 ]+")) {
            throw BizException.build("displayName format is wrong!");
        }

        InfStore temp = this.checkDisplayNameExists(CheckDisplayNameExistBO.builder().displayName(infStoreEditBO.getDisplayName()).influencerId(infStoreEditBO.getInfluencerId()).build());
        if (Objects.nonNull(temp)) {
            throw BizException.build("Display Name already exists");
        }
        InfStore influencerStore = BeanUtil.copyProperties(infStoreEditBO, InfStore.class);
        this.updateById(influencerStore);
        return BeanUtil.copyProperties(influencerStore, InfStoreDTO.class);
    }

    @Override
    public InfStoreDTO queryById(IdParam idParam) {
        InfStore store = this.lambdaQuery().select(InfStore::getId, InfStore::getTitle,
                InfStore::getInfluencerId, InfStore::getCover, InfStore::getDisplayName,
                InfStore::getDescription, InfStore::getGoodsSortType).eq(InfStore::getInfluencerId, idParam.getId()).one();
        return BeanUtil.copyProperties(store, InfStoreDTO.class);
    }

    @Override
    public Boolean deleteByInfluencerId(Long influencerId) {
        return this.remove(new LambdaQueryWrapper<InfStore>().eq(InfStore::getInfluencerId, influencerId));
    }

    @Override
    public Boolean changeCover(InfStoreChangeCoverBO infStoreChangeCoverBO) {
        return this.lambdaUpdate().set(InfStore::getCover, infStoreChangeCoverBO.getCover()).eq(InfStore::getInfluencerId, infStoreChangeCoverBO.getInfluencerId()).update();
    }

    public List<GoodsSortTypeDTO> queryGoodSortTypes() {
        List<GoodsSortTypeDTO> goodsSortTypes = new ArrayList<>();
        for (GoodsSortType goodsSortType : GoodsSortType.values()) {
            goodsSortTypes.add(GoodsSortTypeDTO.builder()
                    .sortType(goodsSortType.getSortType())
                    .name(goodsSortType.getName()).build());
        }
        return goodsSortTypes;
    }

    @Override
    public InfStoreDTO queryByDisplayName(InfStoreQueryByDisplayNameBO infStoreQueryByDisplayNameBO) {
        String displayName = infStoreQueryByDisplayNameBO.getDisplayName();
        displayName = displayName.replace("_", " ");
        InfStore store = this.lambdaQuery().select(InfStore::getId, InfStore::getTitle, InfStore::getInfluencerId, InfStore::getCover, InfStore::getDisplayName, InfStore::getDescription, InfStore::getGoodsSortType)
                .apply("lower(display_name) ='" + displayName.toLowerCase() + "'").one();
        if (Objects.isNull(store)) {
            throw BizException.build("Store not found!");
        }
        InfUserInfoDTO infUser = infUserService.queryById(IdParam.builder().id(store.getInfluencerId()).build());
        InfStoreDTO storeDTO = BeanUtil.copyProperties(store, InfStoreDTO.class);
        storeDTO.setInfluencerCode(infUser.getCode());
        return storeDTO;
    }

    @Override
    public InfStoreIdDTO queryStoreIdByInfluencerId(InfStoreQueryByInfIdBO infStoreQueryByInfIdBO) {
        Optional<InfStore> store = this.lambdaQuery().select(InfStore::getId, InfStore::getGoodsSortType).eq(InfStore::getInfluencerId, infStoreQueryByInfIdBO.getInfluencerId()).list().stream().findFirst();
        return store.map(influencerStore -> BeanUtil.copyProperties(influencerStore, InfStoreIdDTO.class)).orElse(null);
    }
}
