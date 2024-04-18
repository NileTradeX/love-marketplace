package com.love.goods.client;

import com.love.common.page.Pageable;
import com.love.goods.bo.AttrNameQueryPageBO;
import com.love.goods.bo.AttrNameSaveBO;
import com.love.goods.dto.AttrNameDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "goods-service-api", contextId = "attrNameFeignClient", path = "attribute")
public interface AttrNameFeignClient {

    @PostMapping("save")
    Long save(AttrNameSaveBO attrNameSaveBO);

    @GetMapping("page")
    Pageable<AttrNameDTO> page(@SpringQueryMap AttrNameQueryPageBO attrNameQueryPageBO);
}
