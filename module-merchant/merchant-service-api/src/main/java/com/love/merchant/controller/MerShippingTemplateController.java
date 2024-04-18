package com.love.merchant.controller;

import com.love.common.result.Result;
import com.love.merchant.bo.MerShippingTemplateBO;
import com.love.merchant.bo.MerShippingTemplateQueryBO;
import com.love.merchant.dto.MerShippingTemplateDTO;
import com.love.merchant.service.MerShippingTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mer/shippingTemplate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerShippingTemplateController {

    private final MerShippingTemplateService merShippingTemplateService;

    @PostMapping("save")
    public Result<Boolean> save(@RequestBody MerShippingTemplateBO merShippingTemplateBO) {
        return Result.success(merShippingTemplateService.save(merShippingTemplateBO));
    }

    @PostMapping("edit")
    public Result<Boolean> edit(@RequestBody MerShippingTemplateBO merShippingTemplateBO) {
        return Result.success(merShippingTemplateService.edit(merShippingTemplateBO));
    }

    @GetMapping("queryByMerchantId")
    public Result<MerShippingTemplateDTO> queryByMerchantId(MerShippingTemplateQueryBO merShippingTemplateQueryBO) {
        return Result.success(merShippingTemplateService.queryByMerchantId(merShippingTemplateQueryBO));
    }

}
