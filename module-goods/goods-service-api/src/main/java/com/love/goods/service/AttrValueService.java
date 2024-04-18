package com.love.goods.service;

import com.love.goods.bo.AttrValueSaveBO;
import com.love.goods.dto.AttrValueDTO;

import java.util.List;

public interface AttrValueService {

    Long save(AttrValueSaveBO attrValueSaveBO);

    boolean saveBatch(List<AttrValueSaveBO> attrValueSaveBOList);

    List<AttrValueDTO> queryByAttrNameIdList(List<Long> attrNameIdList);
}
