package com.love.goods.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.goods.bo.*;
import com.love.goods.dto.BrandDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "goods-service-api", contextId = "brandFeignClient", path = "brand")
public interface BrandFeignClient {

    @PostMapping("save")
    Long save(BrandSaveBO brandSaveBO);

    @PostMapping("saveBatch")
    Boolean saveBatch(BrandBatchSaveBO brandBatchSaveBO);

    @GetMapping("queryById")
    BrandDTO detail(@SpringQueryMap IdParam idParam);

    @GetMapping("deleteById")
    Boolean deleteById(@SpringQueryMap IdParam idParam);

    @GetMapping("queryByMerchantId")
    List<BrandDTO> queryByMerchantId(@SpringQueryMap BrandQueryListBO brandQueryListBO);

    @GetMapping("page")
    Pageable<BrandDTO> page(@SpringQueryMap BrandQueryPageBO brandQueryPageBO);

    @GetMapping("disable")
    Boolean disable(@SpringQueryMap BrandUpdateStatusBO brandStatusBO);

    @GetMapping("enable")
    Boolean enabled(@SpringQueryMap BrandUpdateStatusBO brandStatusBO);

    @GetMapping("queryByIds")
    List<BrandDTO> listByIds(@SpringQueryMap IdsParam idsParam);
}
