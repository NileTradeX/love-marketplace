package com.love.payment.service;

import com.love.payment.bo.PaymentCreateBO;
import com.love.payment.bo.PaymentDetailBO;
import com.love.payment.bo.PaymentRefundBO;
import com.love.payment.bo.SplitFundsBO;
import com.love.payment.dto.*;

public interface PaymentService {

    PaymentRefundDTO refund(PaymentRefundBO paymentRefundBO);

    PaymentInfoDTO createPay(PaymentCreateBO paymentCreationBO);

    SplitFundsResultDTO splitFunds(SplitFundsBO splitFundsBO) throws Throwable;

    PaymentRefundDetailDTO getRefundDetail(PaymentDetailBO paymentDetailBO);
}
