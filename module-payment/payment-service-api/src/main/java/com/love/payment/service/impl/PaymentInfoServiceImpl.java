package com.love.payment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.payment.bo.PaymentQueryByOrderNoBO;
import com.love.payment.bo.PaymentQueryByOrderNoListBO;
import com.love.payment.bo.PaymentUpdateByOrderNoBO;
import com.love.payment.dto.PaymentSimpleDTO;
import com.love.payment.entity.PaymentInfo;
import com.love.payment.enums.PaymentStatus;
import com.love.payment.mapper.PaymentInfoMapper;
import com.love.payment.service.PaymentInfoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    private final Logger logger = LoggerFactory.getLogger(PaymentInfoServiceImpl.class);

    @Override
    public boolean save(PaymentInfo paymentInfo) {
        return super.save(paymentInfo);
    }

    @Override
    public Optional<PaymentInfo> queryOneByOrderNo(String orderNo) {
        return this.lambdaQuery().eq(PaymentInfo::getOrderNo, orderNo).oneOpt();
    }

    @Override
    public Boolean updateByOrderNo(PaymentUpdateByOrderNoBO paymentUpdateByOrderNoBO) {
        LambdaUpdateChainWrapper<PaymentInfo> updateWrapper = this.lambdaUpdate().eq(PaymentInfo::getOrderNo, paymentUpdateByOrderNoBO.getOrderNo());
        if (Objects.nonNull(paymentUpdateByOrderNoBO.getType())) {
            updateWrapper.set(PaymentInfo::getType, paymentUpdateByOrderNoBO.getType());
        }

        if (Objects.nonNull(paymentUpdateByOrderNoBO.getPayTime())) {
            updateWrapper.set(PaymentInfo::getPayTime, paymentUpdateByOrderNoBO.getPayTime());
        }

        if (Objects.nonNull(paymentUpdateByOrderNoBO.getStatus())) {
            updateWrapper.set(PaymentInfo::getStatus, paymentUpdateByOrderNoBO.getStatus());
        }
        updateWrapper.set(PaymentInfo::getUpdateTime, LocalDateTime.now());
        return updateWrapper.update();
    }

    @Override
    public PaymentSimpleDTO queryByOrderNo(PaymentQueryByOrderNoBO paymentQueryByOrderNoBO) {
        PaymentInfo paymentInfo = this.lambdaQuery().eq(PaymentInfo::getOrderNo, paymentQueryByOrderNoBO.getOrderNo()).one();
        if (Objects.isNull(paymentInfo)) {
            logger.warn("payment info for order: {} does not exist", paymentQueryByOrderNoBO.getOrderNo());
            return null;
        }
        return BeanUtil.copyProperties(paymentInfo, PaymentSimpleDTO.class);
    }

    @Override
    public List<PaymentSimpleDTO> queryListByOrderNos(PaymentQueryByOrderNoListBO paymentQueryByOrderNoListBO) {
        if (CollUtil.isEmpty(paymentQueryByOrderNoListBO.getOrderNoList())) {
            return Lists.newArrayList();
        }

        List<PaymentInfo> paymentInfos = this.lambdaQuery().in(PaymentInfo::getOrderNo, paymentQueryByOrderNoListBO.getOrderNoList())
                .eq(PaymentInfo::getStatus, PaymentStatus.SUCCEEDED.getStatus())
                .list();

        return BeanUtil.copyToList(paymentInfos, PaymentSimpleDTO.class);
    }
}
