package com.love.influencer.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.love.api.common.util.JwtUtil;
import com.love.common.Constants;
import com.love.common.enums.ExpireTime;
import com.love.common.page.Pageable;
import com.love.common.param.ByUserIdParam;
import com.love.common.param.IdParam;
import com.love.common.user.SimpleUser;
import com.love.common.util.PageableUtil;
import com.love.common.util.RedisUtil;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.dto.GoodsSimpleDTO;
import com.love.influencer.backend.model.param.*;
import com.love.influencer.backend.model.vo.InfUserInfoVO;
import com.love.influencer.backend.model.vo.InfUserVO;
import com.love.influencer.backend.model.vo.LoginUserVO;
import com.love.influencer.bo.*;
import com.love.influencer.client.InfGoodsFeignClient;
import com.love.influencer.client.InfStoreFeignClient;
import com.love.influencer.client.InfUserFeignClient;
import com.love.influencer.dto.InfStoreDTO;
import com.love.influencer.dto.InfStoreIdDTO;
import com.love.influencer.dto.InfUserDTO;
import com.love.influencer.dto.InfUserInfoDTO;
import com.love.influencer.enums.GoodsStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfluencerUserManager {

    private final RedisUtil redisUtil;
    private final InfUserFeignClient infUserFeignClient;
    private final InfStoreFeignClient infStoreFeignClient;
    private final InfGoodsFeignClient infGoodsFeignClient;
    private final GoodsFeignClient goodsFeignClient;

    public InfUserVO save(InfUserSaveParam infUserSaveParam) {
        InfUserSaveBO infUserSaveBO = BeanUtil.copyProperties(infUserSaveParam, InfUserSaveBO.class);
        InfUserDTO infUserDTO = infUserFeignClient.save(infUserSaveBO);
        return BeanUtil.copyProperties(infUserDTO, InfUserVO.class);
    }

    public Boolean edit(InfUserEditParam infUserEditParam) {
        InfUserEditBO infUserEditBO = BeanUtil.copyProperties(infUserEditParam, InfUserEditBO.class);
        return infUserFeignClient.edit(infUserEditBO);
    }

    public InfUserInfoVO queryById(ByUserIdParam byUserIdParam) {
        InfUserInfoDTO infUserInfoDTO = infUserFeignClient.queryById(IdParam.builder().id(byUserIdParam.getUserId()).build());
        InfStoreIdDTO infStoreIdDTO = infStoreFeignClient.queryStoreIdByInfluencerId(InfStoreQueryByInfIdBO.builder().influencerId(byUserIdParam.getUserId()).build());
        if (Objects.nonNull(infStoreIdDTO)) {
            infUserInfoDTO.setStoreId(infStoreIdDTO.getId());
        }
        return BeanUtil.copyProperties(infUserInfoDTO, InfUserInfoVO.class);
    }

    public InfUserVO queryByCode(InfUserQueryByCodeParam infUserQueryByCodeParam) {
        InfUserQueryByCodeBO infUserQueryByCodeBO = BeanUtil.copyProperties(infUserQueryByCodeParam, InfUserQueryByCodeBO.class);
        InfUserDTO infUserDTO = infUserFeignClient.queryByCode(infUserQueryByCodeBO);
        return BeanUtil.copyProperties(infUserDTO, InfUserVO.class);
    }

    public Pageable<InfUserVO> page(InfUserQueryPageParam infUserQueryPageParam) {
        InfUserQueryPageBO infUserQueryPageBO = BeanUtil.copyProperties(infUserQueryPageParam, InfUserQueryPageBO.class);
        Pageable<InfUserDTO> pageable = infUserFeignClient.page(infUserQueryPageBO);
        return PageableUtil.toPage(pageable, InfUserVO.class);
    }

    public Boolean changePassword(InfUserChangePasswordParam infUserChangePasswordParam) {
        InfUserChangePasswordBO infUserChangePasswordBO = BeanUtil.copyProperties(infUserChangePasswordParam, InfUserChangePasswordBO.class);
        return infUserFeignClient.changePassword(infUserChangePasswordBO);
    }

    public Boolean resetPassword(InfUserResetPasswordParam infUserResetPasswordParam) {
        InfUserResetPasswordBO infUserResetPasswordBO = BeanUtil.copyProperties(infUserResetPasswordParam, InfUserResetPasswordBO.class);
        return infUserFeignClient.resetPassword(infUserResetPasswordBO);
    }

    public Boolean deleteById(IdParam idParam) {
        return infUserFeignClient.deleteById(idParam);
    }

    public LoginUserVO login(InfUserLoginParam infUserLoginParam) {
        InfUserLoginBO infUserLoginBO = BeanUtil.copyProperties(infUserLoginParam, InfUserLoginBO.class);
        InfUserDTO influencerUserDTO = infUserFeignClient.login(infUserLoginBO);

        SimpleUser simpleUser = BeanUtil.copyProperties(influencerUserDTO, SimpleUser.class);

        long expire = ExpireTime.ONE_DAY.getTime() / 2L;

        redisUtil.set(Constants.USER_ID + ":" + influencerUserDTO.getId(), simpleUser, expire);

        Map<String, Object> map = new HashMap<>();
        map.put(Constants.USER_ID, simpleUser.getId());
        String token = JwtUtil.createJwt(map, expire * 1000L);
        InfUserVO loginUserVO = BeanUtil.copyProperties(influencerUserDTO, InfUserVO.class);
        return LoginUserVO.builder().user(loginUserVO).token(token).build();
    }

    public Boolean changeAvatar(InfUserChangeAvatarParam infUserChangeAvatarParam) {
        InfUserChangeAvatarBO infUserChangeAvatarBO = BeanUtil.copyProperties(infUserChangeAvatarParam, InfUserChangeAvatarBO.class);
        return infUserFeignClient.changeAvatar(infUserChangeAvatarBO);
    }


    public Boolean register(InfRegisterParam infRegisterParam) {
        InfUserEditBO infUserEditBO = BeanUtil.copyProperties(infRegisterParam, InfUserEditBO.class);
        Boolean result = infUserFeignClient.edit(infUserEditBO);
        if (result) {
            InfStoreSaveBO infStoreSaveBO = BeanUtil.copyProperties(infRegisterParam, InfStoreSaveBO.class);
            infStoreSaveBO.setInfluencerId(infRegisterParam.getId());
            InfStoreDTO infStoreDTO = infStoreFeignClient.save(infStoreSaveBO);
            result = Objects.nonNull(infStoreDTO.getId());
            if (result) {
                List<GoodsSimpleDTO> goodsSimpleList = goodsFeignClient.queryTopXSales(20);
                if (CollectionUtil.isNotEmpty(goodsSimpleList)) {
                    List<InfGoodsSaveBO> list = goodsSimpleList.stream().map(goodsSimple -> {
                        InfGoodsSaveBO infGoodsSaveBO = new InfGoodsSaveBO();
                        infGoodsSaveBO.setGoodsId(goodsSimple.getId());
                        infGoodsSaveBO.setGoodsStatus(goodsSimple.getStatus());
                        infGoodsSaveBO.setMaxPrice(goodsSimple.getMaxPrice());
                        infGoodsSaveBO.setMinPrice(goodsSimple.getMinPrice());
                        infGoodsSaveBO.setSalesVolume(goodsSimple.getSalesVolume());
                        infGoodsSaveBO.setAvailableStock(0);
                        infGoodsSaveBO.setCommunityScore(goodsSimple.getCommunityScore());
                        return infGoodsSaveBO;
                    }).collect(Collectors.toList());

                    InfGoodsBatchSaveBO infGoodsBatchSaveBO = new InfGoodsBatchSaveBO();
                    infGoodsBatchSaveBO.setInfluencerId(infRegisterParam.getId());
                    infGoodsBatchSaveBO.setSelectedGoods(list);
                    infGoodsBatchSaveBO.setStatus(GoodsStatus.RECOMMENDED.getStatus());
                    return infGoodsFeignClient.batchSave(infGoodsBatchSaveBO);
                }
            }
        }
        return false;
    }
}
