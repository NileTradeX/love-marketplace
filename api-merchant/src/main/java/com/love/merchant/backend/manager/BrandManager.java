package com.love.merchant.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.common.param.IdParam;
import com.love.goods.bo.BrandQueryListBO;
import com.love.goods.bo.BrandSaveBO;
import com.love.goods.client.BrandFeignClient;
import com.love.goods.enums.BrandStatus;
import com.love.merchant.backend.model.param.BrandQueryListParam;
import com.love.merchant.backend.model.param.BrandSaveParam;
import com.love.merchant.backend.model.vo.BrandVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BrandManager {

    private final BrandFeignClient brandFeignClient;
    private final MerchantIdManager merchantIdManager;

    public Long save(BrandSaveParam brandSaveParam) {
        BrandSaveBO brandSaveBO = BeanUtil.copyProperties(brandSaveParam, BrandSaveBO.class);
        brandSaveBO.setMerchantId(merchantIdManager.getMerchantId(brandSaveParam.getUserId()));
        brandSaveBO.setStatus(BrandStatus.ENABLE.getStatus());
        return brandFeignClient.save(brandSaveBO);
    }

    public List<BrandVO> list(BrandQueryListParam brandQueryListParam) {
        return BeanUtil.copyToList(brandFeignClient.queryByMerchantId(BrandQueryListBO.builder().merchantId(merchantIdManager.getMerchantId(brandQueryListParam.getUserId())).build()), BrandVO.class);
    }

    public Boolean deleteById(IdParam idParam) {
        return brandFeignClient.deleteById(idParam);
    }
}
