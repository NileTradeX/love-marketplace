package com.love.payment.controller;

import com.love.common.result.Result;
import com.love.payment.bo.*;
import com.love.payment.dto.PaymentInfoDTO;
import com.love.payment.dto.PaymentSimpleDTO;
import com.love.payment.dto.SplitFundsResultDTO;
import com.love.payment.service.PaymentInfoService;
import com.love.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentInfoService paymentInfoService;

    @PostMapping("updateByOrderNo")
    public Result<Boolean> updateByOrderNo(@RequestBody PaymentUpdateByOrderNoBO paymentUpdateByOrderNoBO) {
        return Result.success(paymentInfoService.updateByOrderNo(paymentUpdateByOrderNoBO));
    }

    @GetMapping("queryByOrderNo")
    public Result<PaymentSimpleDTO> queryByOrderNo(PaymentQueryByOrderNoBO paymentQueryByOrderNoBO) {
        return Result.success(paymentInfoService.queryByOrderNo(paymentQueryByOrderNoBO));
    }

    @PostMapping("createPay")
    public Result<PaymentInfoDTO> createPay(@RequestBody PaymentCreateBO paymentCreationBO) {
        return Result.success(paymentService.createPay(paymentCreationBO));
    }

    @PostMapping("splitFunds")
    public Result<SplitFundsResultDTO> splitFunds(@RequestBody SplitFundsBO splitFundsBO) throws Throwable {
        return Result.success(paymentService.splitFunds(splitFundsBO));
    }

    @GetMapping("queryListByOrderNos")
    public Result<List<PaymentSimpleDTO>> queryListByOrderNos(PaymentQueryByOrderNoListBO paymentQueryByOrderNoListBO) {
        return Result.success(paymentInfoService.queryListByOrderNos(paymentQueryByOrderNoListBO));
    }
}
