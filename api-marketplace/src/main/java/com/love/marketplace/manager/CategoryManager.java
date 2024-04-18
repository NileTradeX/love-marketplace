package com.love.marketplace.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.goods.bo.CategoryQueryByPidBO;
import com.love.goods.bo.CategoryQueryTreeBO;
import com.love.goods.client.CategoryFeignClient;
import com.love.goods.dto.CategoryDTO;
import com.love.goods.dto.CategoryTreeDTO;
import com.love.marketplace.model.param.CategoryQueryByPidParam;
import com.love.marketplace.model.param.CategoryQueryTreeParam;
import com.love.marketplace.model.vo.CategoryTreeVO;
import com.love.marketplace.model.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryManager {

    private final CategoryFeignClient categoryFeignClient;

    public List<CategoryVO> queryByPid(CategoryQueryByPidParam categoryQueryByPidParam) {
        CategoryQueryByPidBO categoryQueryByPidBO = BeanUtil.copyProperties(categoryQueryByPidParam, CategoryQueryByPidBO.class);
        List<CategoryDTO> categoryDTOS = categoryFeignClient.queryByPid(categoryQueryByPidBO);
        return BeanUtil.copyToList(categoryDTOS, CategoryVO.class);
    }

    public List<CategoryTreeVO> tree(CategoryQueryTreeParam categoryQueryTreeParam) {
        CategoryQueryTreeBO categoryTreeBO = BeanUtil.copyProperties(categoryQueryTreeParam, CategoryQueryTreeBO.class);
        List<CategoryTreeDTO> list = categoryFeignClient.tree(categoryTreeBO);
        return BeanUtil.copyToList(list, CategoryTreeVO.class);
    }
}
