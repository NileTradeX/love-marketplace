package com.love.goods.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.goods.bo.*;
import com.love.goods.dto.CategoryDTO;
import com.love.goods.dto.CategoryTreeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "goods-service-api", contextId = "categoryFeignClient", path = "category")
public interface CategoryFeignClient {

    @PostMapping("save")
    Long save(CategorySaveBO categorySaveBO);

    @PostMapping("edit")
    CategoryDTO edit(CategoryEditBO categoryEditBO);

    @GetMapping("queryById")
    CategoryDTO detail(@SpringQueryMap IdParam idParam);

    @GetMapping("deleteById")
    Boolean deleteById(@SpringQueryMap IdParam idParam);

    @GetMapping("page")
    Pageable<CategoryDTO> page(@SpringQueryMap CategoryQueryPageBO categoryQueryPageBO);

    @GetMapping("tree")
    List<CategoryTreeDTO> tree(@SpringQueryMap CategoryQueryTreeBO categoryQueryTreeBO);

    @GetMapping("queryByPid")
    List<CategoryDTO> queryByPid(@SpringQueryMap CategoryQueryByPidBO categoryQueryByPidBO);
}
