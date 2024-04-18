package com.love.marketplace.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageableUtil;
import com.love.goods.client.GoodsFeignClient;
import com.love.marketplace.model.param.ReviewQueryPageParam;
import com.love.marketplace.model.param.ReviewSaveParam;
import com.love.marketplace.model.vo.ReviewVO;
import com.love.review.bo.ReviewQueryUserPageBO;
import com.love.review.bo.ReviewSaveBO;
import com.love.review.client.ReviewFeignClient;
import com.love.review.dto.ReviewDTO;
import com.love.review.enums.ReviewType;
import com.love.user.client.UserFeignClient;
import com.love.user.sdk.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewManager {

    private final UserFeignClient userFeignClient;
    private final ReviewFeignClient reviewFeignClient;
    private final GoodsFeignClient goodsFeignClient;

    public Long save(ReviewSaveParam reviewSaveParam) {
        Integer type = reviewSaveParam.getType();
        if (type == ReviewType.GOODS.getType()) {
            reviewSaveParam.setMerchantId(goodsFeignClient.simple(IdParam.builder().id(reviewSaveParam.getRelatedId()).build()).getMerchantId());
        }
        ReviewSaveBO reviewSaveBO = BeanUtil.copyProperties(reviewSaveParam, ReviewSaveBO.class);
        return reviewFeignClient.save(reviewSaveBO);
    }

    public Pageable<ReviewVO> page(ReviewQueryPageParam reviewQueryPageParam) {
        ReviewQueryUserPageBO reviewQueryPageBO = BeanUtil.copyProperties(reviewQueryPageParam, ReviewQueryUserPageBO.class);
        Pageable<ReviewDTO> page = reviewFeignClient.userPage(reviewQueryPageBO);
        return PageableUtil.toPage(page, ReviewVO.class, (src, dst) -> {
            UserDTO user = userFeignClient.simple(IdParam.builder().id(src.getUserId()).build());
            dst.setUser(BeanUtil.copyProperties(user, ReviewVO.User.class));
        });
    }
}
