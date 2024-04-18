package com.love.influencer.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.common.page.Pageable;
import com.love.common.util.PageableUtil;
import com.love.influencer.backend.model.param.InfUserOrderQueryPageParam;
import com.love.influencer.backend.model.param.InfUserOrderSaveParam;
import com.love.influencer.backend.model.vo.InfUserOrderVO;
import com.love.influencer.bo.InfUserOrderQueryPageBO;
import com.love.influencer.bo.InfUserOrderSaveBO;
import com.love.influencer.client.InfUserOrderFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfluencerOrderManager {

    private final InfUserOrderFeignClient infUserOrderFeignClient;

    public Boolean save(InfUserOrderSaveParam infUserOrderSaveParam) {
        return infUserOrderFeignClient.save(BeanUtil.copyProperties(infUserOrderSaveParam, InfUserOrderSaveBO.class));
    }

    public Pageable<InfUserOrderVO> page(InfUserOrderQueryPageParam infUserOrderQueryPageParam) {
        InfUserOrderQueryPageBO infUserOrderQueryPageBO = BeanUtil.copyProperties(infUserOrderQueryPageParam, InfUserOrderQueryPageBO.class);
        infUserOrderQueryPageBO.setInfluencerId(infUserOrderQueryPageParam.getUserId());
        return PageableUtil.toPage(infUserOrderFeignClient.page(infUserOrderQueryPageBO), InfUserOrderVO.class);
    }

}
