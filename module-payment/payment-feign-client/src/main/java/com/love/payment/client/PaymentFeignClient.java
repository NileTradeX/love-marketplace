package com.love.payment.client;

import com.love.payment.bo.*;
import com.love.payment.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "payment-service-api", contextId = "paymentFeignClient", path = "payment")
public interface PaymentFeignClient {
    @GetMapping("detail")
    PaymentDetailDTO detail(@SpringQueryMap PaymentDetailBO paymentDetailBO);

    @PostMapping("create")
    PaymentCreateDTO create(PaymentCreateBO paymentCreationBO);

    @PostMapping("updateByOrderNo")
    Boolean updateByOrderNo(PaymentUpdateByOrderNoBO paymentUpdateByOrderNoBO);

    @GetMapping("queryByOrderNo")
    PaymentSimpleDTO queryByOrderNo(@SpringQueryMap PaymentQueryByOrderNoBO paymentQueryByOrderNoBO);

    @PostMapping("createPay")
    PaymentInfoDTO createPay(PaymentCreateBO paymentCreationBO);

    @PostMapping("splitFunds")
    SplitFundsResultDTO splitFunds(SplitFundsBO splitFundsBO);

    @GetMapping("queryListByOrderNos")
    List<PaymentSimpleDTO> queryListByOrderNos(@SpringQueryMap PaymentQueryByOrderNoListBO paymentQueryByOrderNoListBO);
}