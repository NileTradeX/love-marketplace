package com.love.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageUtil;
import com.love.goods.bo.LabelQueryPageBO;
import com.love.goods.bo.LabelSaveBO;
import com.love.goods.dto.LabelDTO;
import com.love.goods.entity.Label;
import com.love.goods.enums.LabelStatus;
import com.love.goods.enums.LabelType;
import com.love.goods.mapper.LabelMapper;
import com.love.goods.service.LabelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements LabelService {
    @Override
    public Long save(LabelSaveBO labelSaveBO) {
        Label label = BeanUtil.copyProperties(labelSaveBO, Label.class);
        label.setStatus(LabelStatus.ENABLE.getStatus());
        this.save(label);
        return label.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> batchSave(List<LabelSaveBO> labelSaveBOList, LabelType labelType) {
        if (LabelType.INGREDIENT == labelType) {
            return batchSaveIngredient(labelSaveBOList);
        }
        throw BizException.build("unknown issue!");
    }

    @Override
    public boolean enableAll(List<Long> idList) {
        return this.lambdaUpdate().set(Label::getStatus, LabelStatus.ENABLE.getStatus()).in(Label::getId, idList).update();
    }

    @Override
    public boolean deleteById(IdParam idParam) {
        return this.removeById(idParam.getId());
    }

    @Override
    public List<LabelDTO> queryByIds(String idStr, LabelType labelType) {
        List<Long> labelIds = Arrays.stream(idStr.split(",")).map(Long::parseLong).collect(Collectors.toList());
        List<Label> labels = this.lambdaQuery().in(Label::getId, labelIds).eq(Label::getType, labelType.getType()).list();
        return labels.stream().map(x -> LabelDTO.builder().id(x.getId()).name(x.getName()).build()).collect(Collectors.toList());
    }

    @Override
    public Pageable<LabelDTO> page(LabelQueryPageBO labelQueryPageBO) {
        Page<Label> page = this.lambdaQuery().eq(Label::getType, labelQueryPageBO.getType())
                .eq(Objects.nonNull(labelQueryPageBO.getStatus()), Label::getStatus, labelQueryPageBO.getStatus())
                .orderByAsc(Label::getId)
                .page(new Page<>(labelQueryPageBO.getPageNum(), labelQueryPageBO.getPageSize()));
        return PageUtil.toPage(page, LabelDTO.class);
    }

    private List<Long> batchSaveIngredient(List<LabelSaveBO> labelSaveBOList) {
        List<Long> labelIds = new ArrayList<>();
        for (LabelSaveBO labelSaveBO : labelSaveBOList) {
            Label temp = Label.builder().type(LabelType.INGREDIENT.getType()).name(labelSaveBO.getName()).status(LabelStatus.DISABLE.getStatus()).build();
            Long id = this.baseMapper.insertOrReturnId(temp);
            labelIds.add(id);
        }
        return labelIds;
    }
}
