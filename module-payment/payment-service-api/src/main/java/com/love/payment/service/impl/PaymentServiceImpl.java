package com.love.payment.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.adyen.Client;
import com.adyen.Config;
import com.adyen.model.balanceplatform.JSONObject;
import com.adyen.model.checkout.Amount;
import com.adyen.model.checkout.CreatePaymentCaptureRequest;
import com.adyen.model.checkout.Split;
import com.adyen.model.checkout.SplitAmount;
import com.adyen.model.legalentitymanagement.TransferInstrumentReference;
import com.adyen.service.checkout.ModificationsApi;
import com.adyen.service.exception.ApiException;
import com.alibaba.nacos.shaded.com.google.common.collect.Maps;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.love.common.exception.BizException;
import com.love.common.util.GsonUtil;
import com.love.common.util.HttpUtil;
import com.love.payment.bo.*;
import com.love.payment.config.AdyenConfigProperties;
import com.love.payment.dto.*;
import com.love.payment.entity.PaymentInfo;
import com.love.payment.enums.PaymentChannel;
import com.love.payment.enums.PaymentStatus;
import com.love.payment.enums.RefundStatus;
import com.love.payment.service.AdyenMerchantService;
import com.love.payment.service.PaymentInfoService;
import com.love.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PaymentServiceImpl implements PaymentService, InitializingBean {

    static {
        TransferInstrumentReference.openapiFields.add("trustedSource");
        JSONObject.openapiFields.add("interval");
        JSONObject.openapiFields.add("maxAmount");
    }

    private final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);


    private final PaymentInfoService paymentInfoService;

    private final AdyenMerchantService adyenMerchantService;

    @Value("${bolt.checkout.api-key:1b5d31a5022d59b53583a0123cc6c8e37d5969f052d1a5fc1250e40046c74ff8}")
    private String boltApiKey;

    @Value("${bolt.api-url}")
    private String boltApiUrl;

    private final Environment environment;

    private final AdyenConfigProperties adyenConfigProperties;
    private ModificationsApi modificationsApi;

    @Override
    public void afterPropertiesSet() {
        boolean prod = false;
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 1) {
            prod = activeProfiles[0].equalsIgnoreCase("prod");
        }

        AdyenConfigProperties.BalanceAccountApi balanceAccountApi = adyenConfigProperties.getBalanceAccountApi();
        Config balanceAccountConfig = new Config();
        balanceAccountConfig.setUsername(balanceAccountApi.getUsername());
        balanceAccountConfig.setPassword(balanceAccountApi.getPassword());
        balanceAccountConfig.setEnvironment(prod ? com.adyen.enums.Environment.LIVE : com.adyen.enums.Environment.TEST);
        if (prod) {
            balanceAccountConfig.setLiveEndpointUrlPrefix(adyenConfigProperties.getLiveUrlPrefix());
        }

        modificationsApi = new ModificationsApi(new Client(balanceAccountConfig));
    }

    @Override
    public PaymentRefundDTO refund(PaymentRefundBO paymentRefundBO) {
        PaymentInfo paymentInfo = paymentInfoService.queryOneByOrderNo(paymentRefundBO.getOrderNo()).orElseThrow(() -> BizException.build("Order has no payment!"));

        if (paymentInfo.getChannel().equals(PaymentChannel.ADYEN.getStatus())) {
            Map<String, String> headers = Maps.newHashMap();
            headers.put("Content-Type", "application/json");
            headers.put("X-Api-Key", boltApiKey);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                String response = HttpUtil.post(boltApiUrl + "/v1/merchant/transactions/credit",
                        objectMapper.writeValueAsString(BoltCreditBO.builder().amount(paymentRefundBO.getRefundFee())
                                .currency("USD").transactionReference(paymentInfo.getPaymentId()).build()),
                        headers);
                logger.info("bolt credit response:{}", response);
                BoltCreditDTO boltCreditDTO = objectMapper.readValue(response, BoltCreditDTO.class);

                String creditStatus = boltCreditDTO.getCreditDTO().getStatus();
                int status = RefundStatus.FAILED.getStatus();
                if (creditStatus.equals("succeeded")) {
                    status = RefundStatus.SUCCEEDED.getStatus();
                } else if (creditStatus.equals("pending") || creditStatus.equals("in progress")) {
                    status = RefundStatus.PROCESSING.getStatus();
                }

                return PaymentRefundDTO.builder()
                        .id(boltCreditDTO.getId())
                        .amount(boltCreditDTO.getAmountDTO().getAmount())
                        .status(status)
                        .build();
            } catch (Exception e) {
                logger.error("refund fail", e);
                throw BizException.build("Get refund error!");
            }
        }

        throw BizException.build("not support");
    }

    @Override
    public PaymentInfoDTO createPay(PaymentCreateBO paymentCreationBO) {
        if (!paymentInfoService.queryOneByOrderNo(paymentCreationBO.getOrderNo()).isPresent()) {
            paymentInfoService.save(PaymentInfo.builder()
                    .orderNo(paymentCreationBO.getOrderNo())
                    .channel(paymentCreationBO.getChannel())
                    .paymentId(paymentCreationBO.getPaymentId())
                    .merchantId(paymentCreationBO.getMerchantId())
                    .userId(paymentCreationBO.getUserId())
                    .amount(paymentCreationBO.getAmount())
                    .appFee(paymentCreationBO.getAppFee())
                    .status(PaymentStatus.PROCESSING.getStatus())
                    .channel(paymentCreationBO.getChannel())
                    .createTime(LocalDateTime.now())
                    .build());
        }

        return PaymentInfoDTO.builder().orderNo(paymentCreationBO.getOrderNo()).paymentId(paymentCreationBO.getPaymentId()).build();
    }

    @Override
    public SplitFundsResultDTO splitFunds(SplitFundsBO splitFundsBO) {
        CreatePaymentCaptureRequest captureRequest = new CreatePaymentCaptureRequest();
        captureRequest.setMerchantAccount(adyenConfigProperties.getLoveBalanceAccount());
        captureRequest.setAmount(new Amount().value(splitFundsBO.getTotalAmount()).currency(splitFundsBO.getCurrency()));

        List<Split> splits = new ArrayList<>();
        List<SplitFundsBO.SplitItem> items = splitFundsBO.getItems();
        if (CollUtil.isEmpty(items)) {
            throw BizException.build("split info is empty!");
        }

        for (SplitFundsBO.SplitItem item : items) {
            Split split = new Split();
            split.amount(new SplitAmount().value(item.getAmount()));
            split.type(Split.TypeEnum.fromValue(item.getType()));
            split.setReference(item.getReference());
            if (Objects.nonNull(item.getMerchantId())) {
                adyenMerchantService.queryByMerchantId(item.getMerchantId()).ifPresent(adyenMerchant -> split.account(adyenMerchant.getBalanceAccountId()));
                if (Objects.isNull(split.getAccount())) {
                    throw BizException.build("merchant = %s not exist ", item.getMerchantId());
                }
                item.setAccount(split.getAccount());
            }
            splits.add(split);
        }

        captureRequest.setSplits(splits);
        SplitFundsResultDTO splitFundsResultDTO = new SplitFundsResultDTO();
        splitFundsResultDTO.setData(GsonUtil.bean2json(splitFundsBO));
        try {
            modificationsApi.captureAuthorisedPayment(splitFundsBO.getPspReference(), captureRequest);
        } catch (ApiException exception) {
            logger.error("split funds error , code:{} , body: {} ", exception.getStatusCode(), exception.getResponseBody());
            splitFundsResultDTO.setSuccess(false);
            splitFundsResultDTO.setMsg(exception.getMessage());
        } catch (IOException ioException) {
            logger.error("split funds error : ", ioException);
            splitFundsResultDTO.setSuccess(false);
            splitFundsResultDTO.setMsg(ioException.getMessage());
        }
        return splitFundsResultDTO;
    }

    @Override
    public PaymentRefundDetailDTO getRefundDetail(PaymentDetailBO paymentDetailBO) {
        PaymentInfo paymentInfo = paymentInfoService.queryOneByOrderNo(paymentDetailBO.getOrderNo()).orElseThrow(() -> BizException.build("Order has no payment!"));

        if (paymentInfo.getChannel().equals(PaymentChannel.ADYEN.getStatus())) {
            Map<String, String> headers = Maps.newHashMap();
            headers.put("Content-Type", "application/json");
            headers.put("X-Api-Key", boltApiKey);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                String response = HttpUtil.get(boltApiUrl + "/v1/merchant/transactions/" + paymentInfo.getPaymentId(), headers);
                logger.info("bolt query response:{}", response);

                return objectMapper.readValue(response, PaymentRefundDetailDTO.class);
            } catch (Exception e) {
                logger.error("refund fail", e);
                throw BizException.build("Get refund error!");
            }
        }

        return null;
    }
}