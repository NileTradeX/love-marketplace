package com.love.merchant.client;

import com.love.merchant.bo.MerShippingTemplateBO;
import com.love.merchant.bo.MerShippingTemplateQueryBO;
import com.love.merchant.dto.MerShippingTemplateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "merchant-service-api", contextId = "merShippingTemplateFeignClient", path = "mer/shippingTemplate")
public interface MerShippingTemplateFeignClient {

    @PostMapping("save")
    Boolean save(MerShippingTemplateBO merShippingTemplateBO);

    @PostMapping("edit")
    Boolean edit(MerShippingTemplateBO merShippingTemplateBO);

    @GetMapping("queryByMerchantId")
    MerShippingTemplateDTO queryByMerchantId(@SpringQueryMap @Validated MerShippingTemplateQueryBO merShippingTemplateQueryBO);

}
