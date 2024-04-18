package com.love.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.backend.model.param.ReviewApproveParam;
import com.love.backend.model.param.ReviewAuditQueryPageParam;
import com.love.backend.model.param.ReviewRejectParam;
import com.love.backend.model.vo.ReviewStatVO;
import com.love.backend.model.vo.ReviewVO;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.ObjectUtil;
import com.love.common.util.PageableUtil;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.client.GoodsSkuFeignClient;
import com.love.goods.dto.GoodsSimpleDTO;
import com.love.goods.dto.GoodsSkuDTO;
import com.love.order.client.OrderFeignClient;
import com.love.order.dto.OrderItemDTO;
import com.love.review.bo.ReviewApproveBO;
import com.love.review.bo.ReviewQueryAuditPageBO;
import com.love.review.bo.ReviewRejectBO;
import com.love.review.client.ReviewAuditFeignClient;
import com.love.review.client.ReviewFeignClient;
import com.love.review.dto.ReviewDTO;
import com.love.review.dto.ReviewStatDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewAuditManager {

    private final ReviewAuditFeignClient reviewAuditFeignClient;
    private final ReviewFeignClient reviewFeignClient;
    private final GoodsFeignClient goodsFeignClient;
    private final GoodsSkuFeignClient goodsSkuFeignClient;
    private final OrderFeignClient orderFeignClient;

    public Boolean approve(ReviewApproveParam reviewApproveParam) {
        ReviewApproveBO reviewApproveBO = BeanUtil.copyProperties(reviewApproveParam, ReviewApproveBO.class);
        return reviewAuditFeignClient.approve(reviewApproveBO);
    }

    public Boolean reject(ReviewRejectParam reviewRejectParam) {
        ReviewRejectBO reviewRejectBO = BeanUtil.copyProperties(reviewRejectParam, ReviewRejectBO.class);
        return reviewAuditFeignClient.reject(reviewRejectBO);
    }

    public Pageable<ReviewVO> page(ReviewAuditQueryPageParam reviewAuditQueryPageParam) {
        ReviewQueryAuditPageBO reviewAuditQueryPageBO = BeanUtil.copyProperties(reviewAuditQueryPageParam, ReviewQueryAuditPageBO.class);
        Pageable<ReviewDTO> page = reviewAuditFeignClient.page(reviewAuditQueryPageBO);
        return PageableUtil.toPage(page, ReviewVO.class, (src, dst) -> {
            if (!StringUtils.isBlank(src.getRelatedStr())) {
                GoodsSimpleDTO goodsDTO = goodsFeignClient.simple(IdParam.builder().id(src.getRelatedId()).build());
                OrderItemDTO orderItemDTO = orderFeignClient.queryByOrderItemId(IdParam.builder().id(Long.parseLong(src.getRelatedStr().trim())).build());
                GoodsSkuDTO goodsSkuDTO = goodsSkuFeignClient.detail(IdParam.builder().id(orderItemDTO.getSkuId()).build());
                ReviewVO.Goods goods = new ReviewVO.Goods();
                goods.setGoodsId(goodsDTO.getId());
                goods.setGoodsTitle(goodsDTO.getTitle());
                goods.setPrice(goodsSkuDTO.getPrice());
                goods.setSkuImageUrl(ObjectUtil.ifNull(goodsSkuDTO.getCover(), goodsDTO.getWhiteBgImg()));
                goods.setSkuInfo(goodsSkuDTO.getAttrValues());
                goods.setSkuId(goodsSkuDTO.getId());
                dst.setGoods(goods);
            }
        });
    }

    public List<ReviewStatVO> stat() {
        List<ReviewStatDTO> reviewStatDTOS = reviewAuditFeignClient.stat();
        return BeanUtil.copyToList(reviewStatDTOS, ReviewStatVO.class);
    }

    public ReviewVO detail(IdParam idParam) {
        ReviewDTO detail = reviewFeignClient.detail(idParam);
        return BeanUtil.copyProperties(detail, ReviewVO.class);
    }
}
