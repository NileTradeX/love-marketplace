package com.love.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.goods.bo.AttrValueSaveBO;
import com.love.goods.dto.AttrValueDTO;
import com.love.goods.entity.AttrValue;
import com.love.goods.mapper.AttrValueMapper;
import com.love.goods.service.AttrValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class AttrValueServiceImpl extends ServiceImpl<AttrValueMapper, AttrValue> implements AttrValueService {

    @Override
    public Long save(AttrValueSaveBO attrValueSaveBO) {
        AttrValue attrValue = BeanUtil.copyProperties(attrValueSaveBO, AttrValue.class);
        return this.baseMapper.insertOrReturnId(attrValue);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(List<AttrValueSaveBO> attrValueSaveBOList) {
        return this.saveBatch(BeanUtil.copyToList(attrValueSaveBOList, AttrValue.class));
    }

    @Override
    public List<AttrValueDTO> queryByAttrNameIdList(List<Long> attrNameIdList) {
        if (CollUtil.isEmpty(attrNameIdList)) {
            return Collections.emptyList();
        }
        List<AttrValue> attributeValues = this.lambdaQuery().in(AttrValue::getAttrNameId, attrNameIdList).list();
        return BeanUtil.copyToList(attributeValues, AttrValueDTO.class);
    }
}
