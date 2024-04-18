package com.love.influencer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.page.Pageable;
import com.love.common.util.MathUtil;
import com.love.common.util.PageUtil;
import com.love.influencer.bo.*;
import com.love.influencer.dto.DashboardDTO;
import com.love.influencer.dto.InfGoodsSimpleDTO;
import com.love.influencer.dto.InfUserDTO;
import com.love.influencer.dto.InfUserOrderDTO;
import com.love.influencer.entity.*;
import com.love.influencer.mapper.InfUserOrderMapper;
import com.love.influencer.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfUserOrderServiceImpl extends ServiceImpl<InfUserOrderMapper, InfUserOrder> implements InfUserOrderService {
    private final InfUserHitsService infUserHitsService;
    private final InfWithdrawRecordService infWithdrawRecordService;
    private final InfGoodsService infGoodsService;
    private final InfUserService infUserService;

    @Override
    public Boolean save(InfUserOrderSaveBO infUserOrderSaveBO) {
        InfUserOrder infUserOrder = BeanUtil.copyProperties(infUserOrderSaveBO, InfUserOrder.class);
        if (Objects.isNull(infUserOrder.getCommission())) {
            InfUserDTO infUser = infUserService.queryByCode(InfUserQueryByCodeBO.builder().code(infUserOrderSaveBO.getInfluencerCode()).build());
            infUserOrder.setCommissionRate(infUser.getCommissionRate());
            infUserOrder.setCommission(MathUtil.divide(MathUtil.multiply(infUserOrder.getTotalAmount(),
                    MathUtil.multiply(infUserOrder.getCommissionRate(), infUserOrder.getMerCommissionRate())), new BigDecimal("10000")));
            infUserOrder.setInfluencerId(infUser.getId());
        }
        return this.save(infUserOrder);
    }

    @Override
    public Pageable<InfUserOrderDTO> page(InfUserOrderQueryPageBO infUserOrderQueryPageBO) {
        Page<InfUserOrder> page = this.lambdaQuery()
                .eq(Objects.nonNull(infUserOrderQueryPageBO.getInfluencerId()), InfUserOrder::getInfluencerId, infUserOrderQueryPageBO.getInfluencerId())
                .between(Objects.nonNull(infUserOrderQueryPageBO.getBeginTime()) && Objects.nonNull(infUserOrderQueryPageBO.getEndTime()), InfUserOrder::getCreateTime, infUserOrderQueryPageBO.getBeginTime(), infUserOrderQueryPageBO.getEndTime())
                .orderByDesc(InfUserOrder::getCreateTime)
                .page(new Page<>(infUserOrderQueryPageBO.getPageNum(), infUserOrderQueryPageBO.getPageSize()));
        return PageUtil.toPage(page, InfUserOrderDTO.class);
    }

    @Override
    public DashboardDTO dashboard(DashboardQueryBO dashboardQueryBO) {
        DashboardDTO influencerDashboardDTO = new DashboardDTO();
        List<InfUserOrder> userOrders = this.lambdaQuery().eq(InfUserOrder::getInfluencerId, dashboardQueryBO.getUserId()).list();
        LocalDateTime lastWeek = LocalDateTimeUtil.offset(LocalDateTime.now(), -7, ChronoUnit.DAYS);
        LocalDateTime lastMonth = LocalDateTimeUtil.offset(LocalDateTime.now(), -30, ChronoUnit.DAYS);

        if (CollectionUtil.isNotEmpty(userOrders)) {
            influencerDashboardDTO.setTotalTransactionVolume(BigDecimal.valueOf(userOrders.stream().mapToDouble(o -> o.getTotalAmount().doubleValue()).sum()));
            influencerDashboardDTO.setTotalCommissionEarnings(BigDecimal.valueOf(userOrders.stream().mapToDouble(o -> o.getCommission().doubleValue()).sum()));
            influencerDashboardDTO.setWeeklyTransactionVolume(BigDecimal.valueOf(userOrders.stream().filter(o -> o.getCreateTime().isAfter(lastWeek)).mapToDouble(o -> o.getTotalAmount().doubleValue()).sum()));
            influencerDashboardDTO.setWeeklyCommissionEarnings(BigDecimal.valueOf(userOrders.stream().filter(o -> o.getCreateTime().isAfter(lastWeek)).mapToDouble(o -> o.getCommission().doubleValue()).sum()));
            influencerDashboardDTO.setMonthlyTransactionVolume(BigDecimal.valueOf(userOrders.stream().filter(o -> o.getCreateTime().isAfter(lastMonth)).mapToDouble(o -> o.getTotalAmount().doubleValue()).sum()));
            influencerDashboardDTO.setMonthlyCommissionEarnings(BigDecimal.valueOf(userOrders.stream().filter(o -> o.getCreateTime().isAfter(lastMonth)).mapToDouble(o -> o.getCommission().doubleValue()).sum()));
        }
        List<InfUserHits> userHits = infUserHitsService.queryInfluencerById(dashboardQueryBO);
        if (CollectionUtil.isNotEmpty(userHits)) {
            influencerDashboardDTO.setTotalStorefrontClick(userHits.stream().count());
            influencerDashboardDTO.setWeeklyStorefrontClick(userHits.stream().filter(o -> o.getCreateTime().isAfter(lastWeek)).count());
            influencerDashboardDTO.setMonthlyStorefrontClick(userHits.stream().filter(o -> o.getCreateTime().isAfter(lastMonth)).count());
        }
        // balance
        if (CollectionUtil.isNotEmpty(userOrders)) {
            BigDecimal totalBalance = this.baseMapper.calBalance(dashboardQueryBO.getUserId());
            List<InfWithdrawRecord> influencerUserWithdrawRecords = infWithdrawRecordService.queryInfluencerById(dashboardQueryBO);
            BigDecimal totalWithdraw = influencerUserWithdrawRecords.stream().map(InfWithdrawRecord::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            influencerDashboardDTO.setBalance(totalBalance.subtract(totalWithdraw));
        }

        return influencerDashboardDTO;
    }

    @Override
    public Boolean refund(InfUserOrderRefundBO infUserOrderRefundBO) {
        for (InfUserOrderRefundBO.Item item : infUserOrderRefundBO.getItems()) {
            this.baseMapper.refund(infUserOrderRefundBO.getOrderId(), item.getSkuId(), item.getRefundAmount());
        }
        return Boolean.TRUE;
    }
}
