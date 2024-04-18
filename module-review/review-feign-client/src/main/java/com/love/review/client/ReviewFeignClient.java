package com.love.review.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.review.bo.QueryLatestReviewForOrderItemBO;
import com.love.review.bo.ReviewQueryMerchantPageBO;
import com.love.review.bo.ReviewQueryUserPageBO;
import com.love.review.bo.ReviewSaveBO;
import com.love.review.dto.ReviewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "review-service-api", contextId = "reviewFeignClient", path = "review")
public interface ReviewFeignClient {

    @PostMapping("save")
    Long save(ReviewSaveBO reviewSaveBO);

    @GetMapping("queryById")
    ReviewDTO detail(@SpringQueryMap IdParam idParam);

    @GetMapping("deleteById")
    Boolean deleteById(@SpringQueryMap IdParam idParam);

    @GetMapping("userPage")
    Pageable<ReviewDTO> userPage(@SpringQueryMap ReviewQueryUserPageBO userReviewQueryPageBO);

    @GetMapping("merchantPage")
    Pageable<ReviewDTO> merchantPage(@SpringQueryMap ReviewQueryMerchantPageBO merchantReviewQueryPageBO);

    @GetMapping("queryLatestForOrderItem")
    ReviewDTO queryLatestForOrderItem(@SpringQueryMap QueryLatestReviewForOrderItemBO queryLatestReviewForOrderItemBO);
}
