package com.love.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.page.Pageable;
import com.love.common.util.PageUtil;
import com.love.goods.bo.AttrNameQueryPageBO;
import com.love.goods.bo.AttrNameSaveBO;
import com.love.goods.bo.AttrValueSaveBO;
import com.love.goods.dto.AttrNameDTO;
import com.love.goods.dto.AttrValueDTO;
import com.love.goods.entity.AttrName;
import com.love.goods.mapper.AttrNameMapper;
import com.love.goods.service.AttrNameService;
import com.love.goods.service.AttrValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AttrNameServiceImpl extends ServiceImpl<AttrNameMapper, AttrName> implements AttrNameService {

    private final AttrValueService attrValueService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(AttrNameSaveBO attrNameSaveBO) {
        AttrName attrName = BeanUtil.copyProperties(attrNameSaveBO, AttrName.class);
        Long attrNameId = this.baseMapper.insertOrReturnId(attrName);
        List<AttrValueSaveBO> values = attrNameSaveBO.getValues();
        if (CollUtil.isNotEmpty(values)) {
            values.forEach(valueBO -> valueBO.setAttrNameId(attrNameId));
            attrValueService.saveBatch(values);
        }
        return attrNameId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Map<String, JSONObject>> batchSave(List<AttrNameSaveBO> attrNameSaveBOS) {
        Map<String, Map<String, JSONObject>> returnMap = new HashMap<>();
        for (AttrNameSaveBO attrNameSaveBO : attrNameSaveBOS) {
            String name = attrNameSaveBO.getName();

            Map<String, JSONObject> variantsMap = new HashMap<>();
            AttrName attrName = BeanUtil.copyProperties(attrNameSaveBO, AttrName.class);
            attrName.setName(attrName.getName().trim().toLowerCase());
            Long attrNameId = this.baseMapper.insertOrReturnId(attrName);

            List<AttrValueSaveBO> values = attrNameSaveBO.getValues();
            for (AttrValueSaveBO attrValue : values) {
                attrValue.setAttrNameId(attrNameId);
                Long attrValueId = attrValueService.save(attrValue);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("valueId", attrValueId);
                jsonObject.put("value", attrValue.getValue());
                jsonObject.put("attrId", attrNameId);
                jsonObject.put("name", attrName.getName());
                jsonObject.put("sortNum", attrValue.getSortNum());
                variantsMap.put(attrValue.getValue(), jsonObject);
            }
            returnMap.put(name, variantsMap);
        }
        return returnMap;
    }

    @Override
    public Pageable<AttrNameDTO> page(AttrNameQueryPageBO attrNameQueryPageBO) {
        Page<AttrName> page = this.lambdaQuery().page(new Page<>(attrNameQueryPageBO.getPageNum(), attrNameQueryPageBO.getPageSize()));
        if (CollUtil.isEmpty(page.getRecords())) {
            return PageUtil.toPage(page, AttrNameDTO.class);
        }

        List<Long> attrNameIdList = page.getRecords().stream().map(AttrName::getId).collect(Collectors.toList());
        Map<Long, List<AttrValueDTO>> map = attrValueService.queryByAttrNameIdList(attrNameIdList).stream().collect(Collectors.groupingBy(AttrValueDTO::getAttrNameId));

        Pageable<AttrNameDTO> pageable = PageUtil.toPage(page, AttrNameDTO.class);
        pageable.getRecords().forEach(e -> {
            List<AttrValueDTO> valueDTOS = map.get(e.getId());
            if (CollUtil.isNotEmpty(valueDTOS)) {
                e.setValues(new LinkedHashSet<>(valueDTOS));
            }
        });
        return pageable;
    }
}
