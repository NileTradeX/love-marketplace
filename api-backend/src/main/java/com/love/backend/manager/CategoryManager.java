package com.love.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.backend.model.param.CategoryEditParam;
import com.love.backend.model.param.CategoryQueryPageParam;
import com.love.backend.model.param.CategoryQueryTreeParam;
import com.love.backend.model.param.CategorySaveParam;
import com.love.backend.model.vo.CategoryTreeVO;
import com.love.backend.model.vo.CategoryVO;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageableUtil;
import com.love.goods.bo.CategoryEditBO;
import com.love.goods.bo.CategoryQueryPageBO;
import com.love.goods.bo.CategoryQueryTreeBO;
import com.love.goods.bo.CategorySaveBO;
import com.love.goods.client.CategoryFeignClient;
import com.love.goods.dto.CategoryDTO;
import com.love.goods.dto.CategoryTreeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryManager {

    private final CategoryFeignClient categoryFeignClient;

    public Long save(CategorySaveParam categorySaveParam) {
        CategorySaveBO categorySaveBO = BeanUtil.copyProperties(categorySaveParam, CategorySaveBO.class);
        return categoryFeignClient.save(categorySaveBO);
    }

    public CategoryVO edit(CategoryEditParam categoryEditParam) {
        CategoryEditBO categoryEditBO = BeanUtil.copyProperties(categoryEditParam, CategoryEditBO.class);
        return BeanUtil.copyProperties(categoryFeignClient.edit(categoryEditBO), CategoryVO.class);
    }

    public Boolean deleteById(IdParam idParam) {
        return categoryFeignClient.deleteById(idParam);
    }

    public Pageable<CategoryVO> page(CategoryQueryPageParam categoryQueryPageParam) {
        CategoryQueryPageBO categoryQueryPageBO = BeanUtil.copyProperties(categoryQueryPageParam, CategoryQueryPageBO.class);
        Pageable<CategoryDTO> page = categoryFeignClient.page(categoryQueryPageBO);
        return PageableUtil.toPage(page, CategoryVO.class);
    }

    public List<CategoryTreeVO> tree(CategoryQueryTreeParam categoryQueryTreeParam) {
        CategoryQueryTreeBO categoryTreeBO = BeanUtil.copyProperties(categoryQueryTreeParam, CategoryQueryTreeBO.class);
        List<CategoryTreeDTO> list = categoryFeignClient.tree(categoryTreeBO);
        return BeanUtil.copyToList(list, CategoryTreeVO.class);
    }


}
