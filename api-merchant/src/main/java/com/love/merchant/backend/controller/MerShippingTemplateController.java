package com.love.merchant.backend.controller;

import com.love.common.result.Result;
import com.love.merchant.backend.manager.MerShippingTemplateManager;
import com.love.merchant.backend.model.param.MerShippingTemplateParam;
import com.love.merchant.backend.model.param.MerShippingTemplateQueryParam;
import com.love.merchant.dto.MerShippingTemplateDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("mer/shippingTemplate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "MerShippingTemplateApi", description = "all Shipping Template manage operation")
public class MerShippingTemplateController {

    private final MerShippingTemplateManager merShippingTemplateManager;

    @PostMapping("save")
    public Result<Boolean> save(@RequestBody @Validated MerShippingTemplateParam merShippingTemplateParam) {
        return Result.success(merShippingTemplateManager.save(merShippingTemplateParam));
    }

    @PostMapping("edit")
    public Result<Boolean> edit(@RequestBody @Validated MerShippingTemplateParam merShippingTemplateParam) {
        return Result.success(merShippingTemplateManager.edit(merShippingTemplateParam));
    }

    @GetMapping("queryByMerchantId")
    public Result<MerShippingTemplateDTO> queryByMerchantId(MerShippingTemplateQueryParam merShippingTemplateQueryParam) {
        return Result.success(merShippingTemplateManager.queryByMerchantId(merShippingTemplateQueryParam));
    }

}
