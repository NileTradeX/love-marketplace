package com.love.merchant.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.util.GsonUtil;
import com.love.merchant.bo.MerShippingTemplateBO;
import com.love.merchant.bo.MerShippingTemplateQueryBO;
import com.love.merchant.bo.ShippingSettingBO;
import com.love.merchant.dto.MerShippingTemplateDTO;
import com.love.merchant.entity.MerShippingTemplate;
import com.love.merchant.mapper.MerShippingTemplateMapper;
import com.love.merchant.service.MerShippingTemplateService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerShippingTemplateServiceImpl extends ServiceImpl<MerShippingTemplateMapper, MerShippingTemplate> implements MerShippingTemplateService {

    @Override
    public Boolean save(MerShippingTemplateBO merShippingTemplateBO) {
        MerShippingTemplate merShippingTemplate = BeanUtil.copyProperties(merShippingTemplateBO, MerShippingTemplate.class);
        if (Objects.nonNull(merShippingTemplateBO.getSetting())) {
            merShippingTemplate.setSetting(GsonUtil.bean2json(merShippingTemplateBO.getSetting()));
        }
        return this.save(merShippingTemplate);
    }

    @Override
    public Boolean edit(MerShippingTemplateBO merShippingTemplateBO) {
        String settingJson = StringUtils.EMPTY;
        if (Objects.nonNull(merShippingTemplateBO.getSetting())) {
            settingJson = GsonUtil.bean2json(merShippingTemplateBO.getSetting());
        }
        return this.lambdaUpdate().set(Objects.nonNull(merShippingTemplateBO.getShippingModels()), MerShippingTemplate::getShippingModels, merShippingTemplateBO.getShippingModels()).set(Objects.nonNull(merShippingTemplateBO.getSetting()), MerShippingTemplate::getSetting, settingJson).eq(MerShippingTemplate::getMerchantId, merShippingTemplateBO.getMerchantId()).update();
    }

    @Override
    public MerShippingTemplateDTO queryByMerchantId(MerShippingTemplateQueryBO merShippingTemplateQueryBO) {
        MerShippingTemplate merShippingTemplate = this.lambdaQuery().eq(MerShippingTemplate::getMerchantId, merShippingTemplateQueryBO.getMerchantId()).one();
        if (Objects.isNull(merShippingTemplate)) {
            throw BizException.build("Merchant Shipping Template not found!");
        }
        MerShippingTemplateDTO merShippingTemplateDTO = BeanUtil.copyProperties(merShippingTemplate, MerShippingTemplateDTO.class, "setting");
        if (Objects.nonNull(merShippingTemplate.getSetting())) {
            merShippingTemplateDTO.setSetting(GsonUtil.json2bean(merShippingTemplate.getSetting(), ShippingSettingBO.class));
        }
        return merShippingTemplateDTO;
    }
}
