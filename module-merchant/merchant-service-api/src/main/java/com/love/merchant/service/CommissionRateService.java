package com.love.merchant.service;

import com.love.common.page.Pageable;
import com.love.merchant.bo.CommissionRateQueryPageBO;
import com.love.merchant.bo.CommissionRateSaveBO;
import com.love.merchant.dto.CommissionRateDTO;
import com.love.merchant.entity.CommissionRate;

import java.math.BigDecimal;

public interface CommissionRateService {
    boolean saveInner(CommissionRate commissionRate);

    BigDecimal queryCurrent(long adminId);

    Boolean save(CommissionRateSaveBO commissionRateSaveBO);

    Pageable<CommissionRateDTO> page(CommissionRateQueryPageBO commissionRateQueryPageBO);
}
