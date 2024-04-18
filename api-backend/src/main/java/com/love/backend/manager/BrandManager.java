package com.love.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.backend.model.param.BrandQueryListParam;
import com.love.backend.model.vo.BrandVO;
import com.love.goods.bo.BrandQueryListBO;
import com.love.goods.client.BrandFeignClient;
import com.love.goods.dto.BrandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BrandManager {

    private final BrandFeignClient brandFeignClient;

    public List<BrandVO> list(BrandQueryListParam brandQueryListParam) {
        BrandQueryListBO brandQueryListBO = BeanUtil.copyProperties(brandQueryListParam, BrandQueryListBO.class);
        List<BrandDTO> list = brandFeignClient.queryByMerchantId(brandQueryListBO);
        return BeanUtil.copyToList(list, BrandVO.class);
    }
}
