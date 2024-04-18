package com.love.merchant.client;

import com.love.common.page.Pageable;
import com.love.merchant.bo.CommissionRateQueryPageBO;
import com.love.merchant.bo.CommissionRateSaveBO;
import com.love.merchant.dto.CommissionRateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "merchant-service-api", contextId = "commissionRateFeignClient", path = "mer/fee/rate")
public interface CommissionRateFeignClient {

    @PostMapping("save")
    Boolean save(CommissionRateSaveBO commissionRateSaveBO);

    @GetMapping("queryCurrent")
    BigDecimal queryCurrent(@RequestParam("merchantId") Long merchantId);

    @GetMapping("page")
    Pageable<CommissionRateDTO> page(@SpringQueryMap CommissionRateQueryPageBO commissionRateQueryPageBO);
}
