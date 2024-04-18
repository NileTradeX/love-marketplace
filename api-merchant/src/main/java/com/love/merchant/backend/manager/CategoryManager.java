package com.love.merchant.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.common.page.Pageable;
import com.love.common.util.PageableUtil;
import com.love.goods.bo.CategoryQueryPageBO;
import com.love.goods.bo.CategoryQueryTreeBO;
import com.love.goods.client.CategoryFeignClient;
import com.love.goods.dto.CategoryDTO;
import com.love.goods.dto.CategoryTreeDTO;
import com.love.merchant.backend.model.param.CategoryQueryPageParam;
import com.love.merchant.backend.model.param.CategoryQueryTreeParam;
import com.love.merchant.backend.model.vo.CategoryTreeVO;
import com.love.merchant.backend.model.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryManager {

    private final CategoryFeignClient categoryFeignClient;

    public Pageable<CategoryVO> page(CategoryQueryPageParam categoryQueryPageParam) {
        CategoryQueryPageBO categoryQueryPageBO = BeanUtil.copyProperties(categoryQueryPageParam, CategoryQueryPageBO.class);
        Pageable<CategoryDTO> page = categoryFeignClient.page(categoryQueryPageBO);
        return PageableUtil.toPage(page, CategoryVO.class);
    }

    public List<CategoryTreeVO> tree(CategoryQueryTreeParam categoryQueryTreeParam) {
        CategoryQueryTreeBO categoryQueryTreeBO = BeanUtil.copyProperties(categoryQueryTreeParam, CategoryQueryTreeBO.class);
        List<CategoryTreeDTO> categoryTreeDTOS = categoryFeignClient.tree(categoryQueryTreeBO);
        return BeanUtil.copyToList(categoryTreeDTOS, CategoryTreeVO.class);
    }
}
