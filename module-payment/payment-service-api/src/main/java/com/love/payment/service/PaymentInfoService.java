package com.love.payment.service;

import com.love.payment.bo.PaymentQueryByOrderNoBO;
import com.love.payment.bo.PaymentQueryByOrderNoListBO;
import com.love.payment.bo.PaymentUpdateByOrderNoBO;
import com.love.payment.dto.PaymentSimpleDTO;
import com.love.payment.entity.PaymentInfo;

import java.util.List;
import java.util.Optional;

public interface PaymentInfoService {
    boolean save(PaymentInfo paymentInfo);

    Optional<PaymentInfo> queryOneByOrderNo(String orderNo);

    Boolean updateByOrderNo(PaymentUpdateByOrderNoBO paymentUpdateByOrderNoBO);

    PaymentSimpleDTO queryByOrderNo(PaymentQueryByOrderNoBO paymentQueryByOrderNoBO);

    List<PaymentSimpleDTO> queryListByOrderNos(PaymentQueryByOrderNoListBO paymentQueryByOrderNoListBO);
}
