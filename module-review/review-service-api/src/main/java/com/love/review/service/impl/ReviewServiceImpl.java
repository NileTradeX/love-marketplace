package com.love.review.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageUtil;
import com.love.review.bo.*;
import com.love.review.dto.ReviewDTO;
import com.love.review.dto.ReviewStatDTO;
import com.love.review.entity.Review;
import com.love.review.enums.ReviewAuditStatus;
import com.love.review.mapper.ReviewMapper;
import com.love.review.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

    private static final Integer QUERY_FROM_PDP = 0;
    private static final Integer QUERY_FROM_ORP = 1;

    @Override
    public Long save(ReviewSaveBO reviewSaveBO) {
        Review review = BeanUtil.copyProperties(reviewSaveBO, Review.class);
        this.save(review);
        return review.getId();
    }

    @Override
    public ReviewDTO queryById(IdParam idParam) {
        return BeanUtil.copyProperties(this.getById(idParam.getId()), ReviewDTO.class);
    }

    @Override
    public boolean deleteById(IdParam idParam) {
        return this.removeById(idParam.getId());
    }

    @Override
    public Pageable<ReviewDTO> userPage(ReviewQueryUserPageBO reviewQueryUserPageBO) {
        LambdaQueryChainWrapper<Review> queryWrapper = this.lambdaQuery().eq(Review::getType, reviewQueryUserPageBO.getType())
                .eq(Review::getRelatedId, reviewQueryUserPageBO.getRelatedId()).eq(Objects.nonNull(reviewQueryUserPageBO.getRelatedStr()), Review::getRelatedStr, reviewQueryUserPageBO.getRelatedStr());

        int from = reviewQueryUserPageBO.getFrom();
        if (QUERY_FROM_PDP == from) {
            queryWrapper.eq(Review::getAuditStatus, ReviewAuditStatus.APPROVED.getStatus());
        } else if (QUERY_FROM_ORP == from) {
            if (Objects.isNull(reviewQueryUserPageBO.getUserId())) {
                throw BizException.build("user id cannot be null");
            }
            queryWrapper.eq(Review::getUserId, reviewQueryUserPageBO.getUserId());
        }

        Page<Review> page = queryWrapper.page(new Page<>(reviewQueryUserPageBO.getPageNum(), reviewQueryUserPageBO.getPageSize()));
        return PageUtil.toPage(page, ReviewDTO.class);
    }

    @Override
    public Pageable<ReviewDTO> merchantPage(ReviewQueryMerchantPageBO reviewQueryMerchantPageBO) {
        Page<Review> page = this.lambdaQuery()
                .eq(Review::getMerchantId, reviewQueryMerchantPageBO.getMerchantId())
                .eq(Objects.nonNull(reviewQueryMerchantPageBO.getType()), Review::getType, reviewQueryMerchantPageBO.getType())
                .eq(Objects.nonNull(reviewQueryMerchantPageBO.getRelatedId()), Review::getRelatedId, reviewQueryMerchantPageBO.getRelatedId())
                .eq(Review::getAuditStatus, ReviewAuditStatus.APPROVED.getStatus())
                .page(new Page<>(reviewQueryMerchantPageBO.getPageNum(), reviewQueryMerchantPageBO.getPageSize()));
        return PageUtil.toPage(page, ReviewDTO.class);
    }

    @Override
    public Pageable<ReviewDTO> auditPage(ReviewQueryAuditPageBO reviewQueryAuditPageBO) {
        LambdaQueryChainWrapper<Review> queryWrapper = this.lambdaQuery();
        queryWrapper.eq(Objects.nonNull(reviewQueryAuditPageBO.getStatus()), Review::getAuditStatus, reviewQueryAuditPageBO.getStatus());

        if (Objects.nonNull(reviewQueryAuditPageBO.getStatus())) {
            if (reviewQueryAuditPageBO.getStatus().equals(ReviewAuditStatus.PENDING.getStatus())) {
                queryWrapper.orderByDesc(Review::getCreateTime);
            } else {
                queryWrapper.orderByDesc(Review::getAuditTime);
            }
        }

        return PageUtil.toPage(queryWrapper.page(new Page<>(reviewQueryAuditPageBO.getPageNum(), reviewQueryAuditPageBO.getPageSize())), ReviewDTO.class);
    }

    @Override
    public List<ReviewStatDTO> stat() {
        Long numAwaiting = this.lambdaQuery()
                .eq(Review::getAuditStatus, ReviewAuditStatus.PENDING.getStatus())
                .count();
        Long numRejected = this.lambdaQuery()
                .eq(Review::getAuditStatus, ReviewAuditStatus.REJECTED.getStatus())
                .count();
        List<ReviewStatDTO> reviewStatusDTOList = new ArrayList<>(4);
        reviewStatusDTOList.add(
                ReviewStatDTO.builder()
                        .name("All")
                        .build());
        reviewStatusDTOList.add(
                ReviewStatDTO.builder()
                        .status(ReviewAuditStatus.PENDING.getStatus())
                        .name("Pending")
                        .number(numAwaiting)
                        .build());
        reviewStatusDTOList.add(
                ReviewStatDTO.builder()
                        .status(ReviewAuditStatus.APPROVED.getStatus())
                        .name("Approved")
                        .build());
        reviewStatusDTOList.add(
                ReviewStatDTO.builder()
                        .status(ReviewAuditStatus.REJECTED.getStatus())
                        .name("Rejected")
                        .number(numRejected)
                        .build());
        return reviewStatusDTOList;
    }

    @Override
    public boolean approve(ReviewApproveBO reviewApproveBO) {
        return this.lambdaUpdate()
                .set(Review::getAuditStatus, ReviewAuditStatus.APPROVED.getStatus())
                .set(Review::getAuditTime, LocalDateTime.now())
                .eq(Review::getId, reviewApproveBO.getId())
                .eq(Review::getAuditStatus, ReviewAuditStatus.PENDING.getStatus())
                .update();
    }

    @Override
    public boolean reject(ReviewRejectBO reviewRejectBO) {
        return this.lambdaUpdate()
                .set(Review::getAuditStatus, ReviewAuditStatus.REJECTED.getStatus())
                .set(Review::getAuditComment, reviewRejectBO.getComment())
                .set(Review::getAuditTime, LocalDateTime.now())
                .eq(Review::getId, reviewRejectBO.getId())
                .eq(Review::getAuditStatus, ReviewAuditStatus.PENDING.getStatus())
                .update();
    }

    @Override
    public ReviewDTO queryLatestForOrderItem(QueryLatestReviewForOrderItemBO queryLatestReviewForOrderItemBO) {
        Review review = this.lambdaQuery().eq(Review::getUserId, queryLatestReviewForOrderItemBO.getUserId())
                .eq(Review::getRelatedId, queryLatestReviewForOrderItemBO.getRelatedId())
                .eq(Review::getRelatedStr, queryLatestReviewForOrderItemBO.getRelatedStr()).last("limit 1")
                .orderByDesc(Review::getCreateTime)
                .one();
        if (Objects.nonNull(review)) {
            return BeanUtil.copyProperties(review, ReviewDTO.class);
        }
        return null;
    }
}
