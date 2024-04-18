package com.love.goods.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.goods.bo.BrandQueryListBO;
import com.love.goods.bo.BrandQueryPageBO;
import com.love.goods.bo.BrandSaveBO;
import com.love.goods.dto.BrandDTO;
import com.love.goods.enums.BrandStatus;

import java.util.List;

public interface BrandService {

    Long save(BrandSaveBO brandSaveBO);

    Boolean saveBatch(List<BrandSaveBO> brandSaveBOList);

    BrandDTO queryById(IdParam idParam);

    boolean deleteById(IdParam idParam);

    List<BrandDTO> queryByMerchantId(BrandQueryListBO brandQueryListBO);

    Pageable<BrandDTO> page(BrandQueryPageBO pageParam);

    boolean updateStatus(Long merchantId, BrandStatus brandStatus);

    List<BrandDTO> queryByIds(IdsParam idsParam);
}
