package com.love.marketplace.controller;

import com.love.common.result.Result;
import com.love.marketplace.manager.PaymentManager;
import com.love.marketplace.model.param.BoltPaymentCreateParam;
import com.love.marketplace.model.param.PayInfoCreateParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "PaymentApi", description = "All Payment Api operation")
public class PaymentController {

    private final PaymentManager paymentManager;

    @PostMapping("bolt/create")
    public Result<Void> createBoltPayment(@RequestBody BoltPaymentCreateParam boltPaymentCreateParam) {
        PayInfoCreateParam payInfoCreateParam = PayInfoCreateParam.builder()
                .orderNo(boltPaymentCreateParam.getOrderReference())
                .totalAmount(boltPaymentCreateParam.getTotalAmount())
                .appFee(0L).channel(1).paymentId(boltPaymentCreateParam.getTransactionReference()).build();
        paymentManager.createPay(payInfoCreateParam);
        return Result.success();
    }
}
