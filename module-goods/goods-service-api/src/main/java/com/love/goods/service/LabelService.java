package com.love.goods.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.goods.bo.LabelQueryPageBO;
import com.love.goods.bo.LabelSaveBO;
import com.love.goods.dto.LabelDTO;
import com.love.goods.enums.LabelType;

import java.util.List;

public interface LabelService {

    Long save(LabelSaveBO saveBO);

    List<Long> batchSave(List<LabelSaveBO> labelSaveBOList, LabelType labelType);

    boolean enableAll(List<Long> ids);

    boolean deleteById(IdParam idParam);

    List<LabelDTO> queryByIds(String idStr, LabelType labelType);

    Pageable<LabelDTO> page(LabelQueryPageBO labelQueryPageBO);
}
