package com.love.payment.controller;

import com.love.common.result.Result;
import com.love.payment.bo.PaymentDetailBO;
import com.love.payment.bo.PaymentRefundBO;
import com.love.payment.dto.PaymentRefundDTO;
import com.love.payment.dto.PaymentRefundDetailDTO;
import com.love.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("refund")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RefundController {
    private final PaymentService paymentService;

    @PostMapping("create")
    public Result<PaymentRefundDTO> create(@RequestBody PaymentRefundBO paymentRefundBO) {
        return Result.success(paymentService.refund(paymentRefundBO));
    }

    @GetMapping("getRefundDetail")
    public Result<PaymentRefundDetailDTO> getRefundDetail(PaymentDetailBO paymentDetailBO) {
        return Result.success(paymentService.getRefundDetail(paymentDetailBO));
    }
}
