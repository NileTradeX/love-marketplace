package com.love.goods.service;

import com.alibaba.fastjson.JSONObject;
import com.love.common.page.Pageable;
import com.love.goods.bo.AttrNameQueryPageBO;
import com.love.goods.bo.AttrNameSaveBO;
import com.love.goods.dto.AttrNameDTO;

import java.util.List;
import java.util.Map;

public interface AttrNameService {

    Long save(AttrNameSaveBO attrNameSaveBO);

    Map<String, Map<String, JSONObject>> batchSave(List<AttrNameSaveBO> attrNameSaveBOS);

    Pageable<AttrNameDTO> page(AttrNameQueryPageBO attrNameQueryPageBO);
}
