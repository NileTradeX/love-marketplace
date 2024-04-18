package com.love.goods.client;

import com.love.common.page.Pageable;
import com.love.goods.bo.LabelQueryPageBO;
import com.love.goods.dto.LabelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "goods-service-api", contextId = "labelFeignClient", path = "label")
public interface LabelFeignClient {
    @GetMapping("page")
    Pageable<LabelDTO> page(@SpringQueryMap LabelQueryPageBO labelQueryPageBO);
}
