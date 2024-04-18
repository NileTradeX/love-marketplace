package com.love.merchant.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.merchant.bo.MerPermEditBO;
import com.love.merchant.bo.MerPermQueryPageBO;
import com.love.merchant.bo.MerPermSaveBO;
import com.love.merchant.dto.MerPermDTO;

import java.util.List;

public interface MerPermService {

    MerPermDTO save(MerPermSaveBO merPermSaveBO);

    MerPermDTO edit(MerPermEditBO merPermEditBO);

    MerPermDTO queryById(IdParam idParam);

    Boolean deleteById(IdParam idParam);

    Pageable<MerPermDTO> page(MerPermQueryPageBO merPermQueryPageBO);

    List<MerPermDTO> tree(Long roleId, boolean isSuper);
}
