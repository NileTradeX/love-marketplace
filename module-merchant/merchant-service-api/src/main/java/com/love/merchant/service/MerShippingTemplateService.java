package com.love.merchant.service;

import com.love.merchant.bo.MerShippingTemplateBO;
import com.love.merchant.bo.MerShippingTemplateQueryBO;
import com.love.merchant.dto.MerShippingTemplateDTO;

public interface MerShippingTemplateService {

    Boolean save(MerShippingTemplateBO merShippingTemplateBO);

    Boolean edit(MerShippingTemplateBO merShippingTemplateBO);

    MerShippingTemplateDTO queryByMerchantId(MerShippingTemplateQueryBO merShippingTemplateQueryBO);

}
