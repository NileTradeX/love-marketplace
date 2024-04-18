package com.love.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.backend.model.param.LabelQueryPageParam;
import com.love.backend.model.vo.LabelVO;
import com.love.common.page.Pageable;
import com.love.common.util.PageableUtil;
import com.love.goods.bo.LabelQueryPageBO;
import com.love.goods.client.LabelFeignClient;
import com.love.goods.dto.LabelDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LabelManager {

    private final LabelFeignClient labelFeignClient;

    public Pageable<LabelVO> page(LabelQueryPageParam labelQueryPageParam) {
        LabelQueryPageBO labelQueryPageBO = BeanUtil.copyProperties(labelQueryPageParam, LabelQueryPageBO.class);
        Pageable<LabelDTO> page = labelFeignClient.page(labelQueryPageBO);
        return PageableUtil.toPage(page, LabelVO.class);
    }
}
