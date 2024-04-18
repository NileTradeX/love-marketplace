package com.love.merchant.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.merchant.bo.MerPermEditBO;
import com.love.merchant.bo.MerPermQueryPageBO;
import com.love.merchant.bo.MerPermSaveBO;
import com.love.merchant.dto.MerPermDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "merchant-service-api", contextId = "merchantPermFeignClient", path = "mer/perm")
public interface MerchantPermFeignClient {

    @PostMapping("save")
    MerPermDTO save(MerPermSaveBO sysPermSaveBO);

    @PostMapping("edit")
    MerPermDTO edit(MerPermEditBO sysPermEditBO);

    @GetMapping("queryById")
    Result<MerPermDTO> queryById(@SpringQueryMap IdParam idParam);

    @GetMapping("deleteById")
    Boolean deleteById(@SpringQueryMap IdParam idParam);

    @GetMapping("page")
    Pageable<MerPermDTO> page(@SpringQueryMap MerPermQueryPageBO merPermQueryPageBO);

    @GetMapping("tree")
    List<MerPermDTO> tree(@SpringQueryMap IdParam idParam);
}
