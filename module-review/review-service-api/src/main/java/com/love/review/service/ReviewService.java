package com.love.review.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.review.bo.*;
import com.love.review.dto.ReviewDTO;
import com.love.review.dto.ReviewStatDTO;

import java.util.List;

public interface ReviewService {

    Long save(ReviewSaveBO reviewSaveBO);

    ReviewDTO queryById(IdParam idParam);

    boolean deleteById(IdParam idParam);

    Pageable<ReviewDTO> userPage(ReviewQueryUserPageBO reviewQueryUserPageBO);

    Pageable<ReviewDTO> merchantPage(ReviewQueryMerchantPageBO reviewQueryMerchantPageBO);

    Pageable<ReviewDTO> auditPage(ReviewQueryAuditPageBO reviewQueryAuditPageBO);

    List<ReviewStatDTO> stat();

    boolean approve(ReviewApproveBO reviewApproveBO);

    boolean reject(ReviewRejectBO reviewRejectBO);

    ReviewDTO queryLatestForOrderItem(QueryLatestReviewForOrderItemBO queryLatestReviewForOrderItemBO);
}
