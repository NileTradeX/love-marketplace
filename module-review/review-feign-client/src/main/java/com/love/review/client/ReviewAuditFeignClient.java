package com.love.review.client;

import com.love.common.page.Pageable;
import com.love.review.bo.ReviewApproveBO;
import com.love.review.bo.ReviewQueryAuditPageBO;
import com.love.review.bo.ReviewRejectBO;
import com.love.review.dto.ReviewDTO;
import com.love.review.dto.ReviewStatDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "review-service-api", contextId = "reviewAuditFeignClient", path = "review/audit")
public interface ReviewAuditFeignClient {
    @PostMapping("approve")
    Boolean approve(ReviewApproveBO reviewApproveBO);

    @PostMapping("reject")
    Boolean reject(ReviewRejectBO reviewRejectBO);

    @GetMapping("page")
    Pageable<ReviewDTO> page(@SpringQueryMap ReviewQueryAuditPageBO reviewAuditQueryPageBO);

    @GetMapping("stat")
    List<ReviewStatDTO> stat();
}
