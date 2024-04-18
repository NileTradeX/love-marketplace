package com.love.merchant.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageableUtil;
import com.love.merchant.backend.model.param.ReviewQueryPageParam;
import com.love.merchant.backend.model.vo.ReviewVO;
import com.love.review.bo.ReviewQueryMerchantPageBO;
import com.love.review.client.ReviewFeignClient;
import com.love.review.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewManager {

    private final ReviewFeignClient reviewFeignClient;

    public ReviewVO detail(IdParam idParam) {
        return BeanUtil.copyProperties(reviewFeignClient.detail(idParam), ReviewVO.class);
    }

    public Boolean deleteById(IdParam idParam) {
        return reviewFeignClient.deleteById(idParam);
    }

    public Pageable<ReviewVO> page(ReviewQueryPageParam reviewQueryPageParam) {
        ReviewQueryMerchantPageBO reviewQueryPageBO = BeanUtil.copyProperties(reviewQueryPageParam, ReviewQueryMerchantPageBO.class);
        Pageable<ReviewDTO> page = reviewFeignClient.merchantPage(reviewQueryPageBO);
        return PageableUtil.toPage(page, ReviewVO.class);
    }
}
