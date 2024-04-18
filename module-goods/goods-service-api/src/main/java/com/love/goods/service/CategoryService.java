package com.love.goods.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.goods.bo.*;
import com.love.goods.dto.CategoryDTO;
import com.love.goods.dto.CategoryTreeDTO;
import com.love.goods.dto.PublishCategoryIdDTO;

import java.util.List;

public interface CategoryService {

    Long save(CategorySaveBO categorySaveBO);

    CategoryDTO edit(CategoryEditBO categoryEditBO);

    CategoryDTO queryById(IdParam idParam);

    boolean deleteById(IdParam idParam);

    boolean deleteByPid(Long pid);

    Pageable<CategoryDTO> page(CategoryQueryPageBO categoryQueryPageBO);

    List<CategoryTreeDTO> tree(CategoryQueryTreeBO categoryQueryTreeBO);

    List<CategoryDTO> queryByPid(CategoryQueryByPidBO categoryQueryByPidBO);

    PublishCategoryIdDTO queryPublishCategoriesById(PublishCategoryQueryBO publishCategoryQueryBO);
}
