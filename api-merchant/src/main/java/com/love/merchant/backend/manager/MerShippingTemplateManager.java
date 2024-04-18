package com.love.merchant.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.merchant.backend.model.param.MerShippingTemplateParam;
import com.love.merchant.backend.model.param.MerShippingTemplateQueryParam;
import com.love.merchant.bo.MerShippingTemplateBO;
import com.love.merchant.bo.MerShippingTemplateQueryBO;
import com.love.merchant.client.MerShippingTemplateFeignClient;
import com.love.merchant.dto.MerShippingTemplateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerShippingTemplateManager {
    private final MerShippingTemplateFeignClient merShippingTemplateFeignClient;

    public Boolean save(MerShippingTemplateParam merShippingTemplateParam) {
        MerShippingTemplateBO merShippingTemplateBO = BeanUtil.copyProperties(merShippingTemplateParam, MerShippingTemplateBO.class);
        merShippingTemplateBO.setMerchantId(merShippingTemplateParam.getUserId());
        return merShippingTemplateFeignClient.save(merShippingTemplateBO);
    }

    public Boolean edit(MerShippingTemplateParam merShippingTemplateParam) {
        MerShippingTemplateBO merShippingTemplateBO = BeanUtil.copyProperties(merShippingTemplateParam, MerShippingTemplateBO.class);
        merShippingTemplateBO.setMerchantId(merShippingTemplateParam.getUserId());
        return merShippingTemplateFeignClient.edit(merShippingTemplateBO);
    }

    public MerShippingTemplateDTO queryByMerchantId(MerShippingTemplateQueryParam merShippingTemplateQueryParam) {
        return merShippingTemplateFeignClient.queryByMerchantId(MerShippingTemplateQueryBO.builder().merchantId(merShippingTemplateQueryParam.getUserId()).build());
    }

}
