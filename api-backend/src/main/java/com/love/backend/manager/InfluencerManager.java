package com.love.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.backend.model.param.InfOrderQueryPageParam;
import com.love.backend.model.param.InfUserQueryPageParam;
import com.love.backend.model.param.InfUserSaveParam;
import com.love.backend.model.vo.InfUserOrderVO;
import com.love.backend.model.vo.InfUserInfoVO;
import com.love.backend.model.vo.InfUserVO;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageableUtil;
import com.love.influencer.bo.InfUserOrderQueryPageBO;
import com.love.influencer.bo.InfUserQueryPageBO;
import com.love.influencer.bo.InfUserSaveBO;
import com.love.influencer.bo.InfUserUpdateStatusBO;
import com.love.influencer.client.InfUserOrderFeignClient;
import com.love.influencer.client.InfUserFeignClient;
import com.love.influencer.dto.InfUserDTO;
import com.love.influencer.dto.InfUserInfoDTO;
import com.love.influencer.dto.InfUserOrderDTO;
import com.love.influencer.enums.InfUserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfluencerManager {

    private final InfUserFeignClient infUserFeignClient;
    private final InfUserOrderFeignClient infUserOrderFeignClient;


    public InfUserVO preSave(InfUserSaveParam infUserSaveParam) {
        InfUserSaveBO infUserSaveBO = BeanUtil.copyProperties(infUserSaveParam, InfUserSaveBO.class);
        InfUserDTO infUserDTO = infUserFeignClient.save(infUserSaveBO);
        return BeanUtil.copyProperties(infUserDTO, InfUserVO.class);
    }

    public Pageable<InfUserVO> page(InfUserQueryPageParam infUserQueryPageParam) {
        InfUserQueryPageBO infUserQueryPageBO = BeanUtil.copyProperties(infUserQueryPageParam, InfUserQueryPageBO.class);
        Pageable<InfUserDTO> pageable = infUserFeignClient.page(infUserQueryPageBO);
        return PageableUtil.toPage(pageable, InfUserVO.class);
    }

    public Boolean active(IdParam idParam) {
        return infUserFeignClient.updateStatus(InfUserUpdateStatusBO.builder().id(idParam.getId()).status(InfUserStatus.ACTIVE.getStatus()).build());
    }

    public Boolean deactive(IdParam idParam) {
        return infUserFeignClient.updateStatus(InfUserUpdateStatusBO.builder().id(idParam.getId()).status(InfUserStatus.INACTIVE.getStatus()).build());
    }

    public InfUserInfoVO detail(IdParam idParam) {
        InfUserInfoDTO infUserInfoDTO = infUserFeignClient.queryById(idParam);
        return BeanUtil.copyProperties(infUserInfoDTO, InfUserInfoVO.class);
    }

    public Pageable<InfUserOrderVO> orders(InfOrderQueryPageParam infOrderQueryPageParam) {
        InfUserOrderQueryPageBO infUserOrderQueryPageBO = BeanUtil.copyProperties(infOrderQueryPageParam, InfUserOrderQueryPageBO.class);
        Pageable<InfUserOrderDTO> pageable = infUserOrderFeignClient.page(infUserOrderQueryPageBO);
        return PageableUtil.toPage(pageable, InfUserOrderVO.class);
    }
}

