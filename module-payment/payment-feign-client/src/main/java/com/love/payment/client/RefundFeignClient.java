package com.love.payment.client;

import com.love.payment.bo.PaymentDetailBO;
import com.love.payment.bo.PaymentRefundBO;
import com.love.payment.dto.PaymentRefundDTO;
import com.love.payment.dto.PaymentRefundDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-service-api", contextId = "refundFeignClient", path = "refund")
public interface RefundFeignClient {
    @PostMapping("create")
    PaymentRefundDTO create(PaymentRefundBO paymentRefundBO);

    @GetMapping("getRefundDetail")
    PaymentRefundDetailDTO getRefundDetail(@SpringQueryMap PaymentDetailBO paymentDetailBO);
}
