package com.love.influencer.client;

import com.love.common.page.Pageable;
import com.love.common.result.Result;
import com.love.influencer.bo.InfUserOrderQueryPageBO;
import com.love.influencer.bo.InfUserOrderRefundBO;
import com.love.influencer.bo.InfUserOrderSaveBO;
import com.love.influencer.dto.InfUserOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "influencer-service-api", contextId = "infUserOrderFeignClient", path = "influencer/order")
public interface InfUserOrderFeignClient {

    @PostMapping("save")
    Boolean save(InfUserOrderSaveBO infUserOrderSaveBO);

    @GetMapping("page")
    Pageable<InfUserOrderDTO> page(@SpringQueryMap InfUserOrderQueryPageBO infUserOrderQueryPageBO);

    @PostMapping("refund")
    Boolean refund(@RequestBody InfUserOrderRefundBO infUserOrderRefundBO);

}
