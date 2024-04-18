package com.love.marketplace.manager;

import cn.hutool.core.bean.BeanUtil;
import com.github.slugify.Slugify;
import com.love.common.page.Pageable;
import com.love.common.param.PageParam;
import com.love.common.util.PageableUtil;
import com.love.goods.bo.BrandQueryPageBO;
import com.love.goods.client.BrandFeignClient;
import com.love.goods.dto.BrandDTO;
import com.love.goods.enums.BrandStatus;
import com.love.marketplace.model.vo.BrandVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BrandManager {

    private final Slugify slugify = new Slugify().withLowerCase(true).withUnderscoreSeparator(false);

    private final BrandFeignClient brandFeignClient;

    public Pageable<BrandVO> page(PageParam pageParam) {
        BrandQueryPageBO brandQueryPageBO = BeanUtil.copyProperties(pageParam, BrandQueryPageBO.class);
        brandQueryPageBO.setStatus(BrandStatus.ENABLE.getStatus());
        Pageable<BrandDTO> page = brandFeignClient.page(brandQueryPageBO);
        page.getRecords().sort(Comparator.comparing(BrandDTO::getName));
        return PageableUtil.toPage(page, BrandVO.class, (src, dst) -> dst.setSlug(slugify.slugify(src.getName())));
    }
}
