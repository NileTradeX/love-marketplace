package com.love.merchant.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.util.PageUtil;
import com.love.common.util.PageableUtil;
import com.love.merchant.bo.CommissionRateQueryPageBO;
import com.love.merchant.bo.CommissionRateSaveBO;
import com.love.merchant.bo.MerUserQueryBO;
import com.love.merchant.dto.CommissionRateDTO;
import com.love.merchant.entity.CommissionRate;
import com.love.merchant.enums.InvitationStatus;
import com.love.merchant.mapper.CommissionRateMapper;
import com.love.merchant.service.CommissionRateService;
import com.love.merchant.service.MerUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommissionRateServiceImpl extends ServiceImpl<CommissionRateMapper, CommissionRate> implements CommissionRateService {

    @Autowired
    private MerUserService merUserService;

    @Override
    public boolean saveInner(CommissionRate commissionRate) {
        return super.save(commissionRate);
    }

    @Override
    public BigDecimal queryCurrent(long adminId) {
        List<CommissionRate> list = this.lambdaQuery().eq(CommissionRate::getAdminId, adminId).orderByDesc(CommissionRate::getEffectiveTime).list();
        if (list.size() == 1) {
            return list.get(0).getRate();
        }

        LocalDateTime now = LocalDateTime.now();
        for (CommissionRate cfr : list) {
            if (now.isAfter(cfr.getEffectiveTime())) {
                return cfr.getRate();
            }
        }
        throw BizException.build("no CommissionRate can use");
    }

    @Override
    public Boolean save(CommissionRateSaveBO commissionRateSaveBO) {
        CommissionRate commissionFeeRate = BeanUtil.copyProperties(commissionRateSaveBO, CommissionRate.class);
        if (commissionFeeRate.getEffectiveTime().isAfter(LocalDateTime.now()) && Objects.isNull(commissionRateSaveBO.getId())) {
            CommissionRate temp = this.lambdaQuery().gt(CommissionRate::getEffectiveTime, LocalDateTime.now()).eq(CommissionRate::getAdminId, commissionRateSaveBO.getAdminId()).one();
            if (Objects.nonNull(temp)) {
                throw BizException.build("already exist one pending rate");
            }
        }
        return super.saveOrUpdate(commissionFeeRate);
    }

    @Override
    public Pageable<CommissionRateDTO> page(CommissionRateQueryPageBO commissionRateQueryPageBO) {
        MerUserQueryBO merUserQueryBO = new MerUserQueryBO();
        merUserQueryBO.setStatus(InvitationStatus.APPROVE.getStatus());
        List<Long> merchantIdList = merUserService.queryApprovedList(merUserQueryBO);
        commissionRateQueryPageBO.setApprovedList(merchantIdList);

        QueryWrapper<CommissionRate> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct admin_id");
        if (StringUtils.isNotBlank(commissionRateQueryPageBO.getBizName())) {
            queryWrapper.like("biz_name", commissionRateQueryPageBO.getBizName());
        }

        if (Objects.nonNull(commissionRateQueryPageBO.getMinRate())) {
            queryWrapper.ge("rate", commissionRateQueryPageBO.getMinRate());
        }

        if (Objects.nonNull(commissionRateQueryPageBO.getMaxRate())) {
            queryWrapper.le("rate", commissionRateQueryPageBO.getMaxRate());
        }

        if (CollUtil.isEmpty(commissionRateQueryPageBO.getApprovedList())) {
            return PageUtil.emptyPage(commissionRateQueryPageBO.getPageNum(), commissionRateQueryPageBO.getPageSize());
        }
        queryWrapper.in("admin_id", commissionRateQueryPageBO.getApprovedList());
        queryWrapper.orderByAsc("admin_id");

        Pageable<CommissionRateDTO> pageable = new Pageable<>();
        List<CommissionRateDTO> data = new ArrayList<>();
        pageable.setRecords(data);

        Page<CommissionRate> page = this.page(new Page<>(commissionRateQueryPageBO.getPageNum(), commissionRateQueryPageBO.getPageSize()), queryWrapper);
        if (!page.getRecords().isEmpty()) {
            page.getRecords().forEach(commissionFeeRate -> {
                CommissionRateDTO commissionRateDTO = new CommissionRateDTO();
                Page<CommissionRate> tempPage = this.lambdaQuery().eq(CommissionRate::getAdminId, commissionFeeRate.getAdminId()).orderByDesc(CommissionRate::getEffectiveTime).page(new Page<>(1, 2));
                List<CommissionRate> list = tempPage.getRecords();

                if (list.size() == 1) {
                    CommissionRate curr = list.get(0);
                    commissionRateDTO.setId(curr.getId());
                    commissionRateDTO.setCurrRate(curr.getRate());
                    commissionRateDTO.setCurrEffectiveTime(curr.getEffectiveTime());
                    commissionRateDTO.setBizName(curr.getBizName());
                    commissionRateDTO.setMerchantId(curr.getAdminId());
                } else if (list.size() == 2) {
                    CommissionRate pending = list.get(0);
                    if (pending.getEffectiveTime().isAfter(LocalDateTime.now())) {
                        commissionRateDTO.setPendingId(pending.getId());
                        commissionRateDTO.setPendingRate(pending.getRate());
                        commissionRateDTO.setPendingEffectiveTime(pending.getEffectiveTime());
                        commissionRateDTO.setBizName(pending.getBizName());
                        commissionRateDTO.setMerchantId(pending.getAdminId());

                        CommissionRate curr = list.get(1);
                        commissionRateDTO.setId(curr.getId());
                        commissionRateDTO.setCurrRate(curr.getRate());
                        commissionRateDTO.setCurrEffectiveTime(curr.getEffectiveTime());
                    } else {
                        commissionRateDTO.setId(pending.getId());
                        commissionRateDTO.setCurrRate(pending.getRate());
                        commissionRateDTO.setCurrEffectiveTime(pending.getEffectiveTime());
                        commissionRateDTO.setBizName(pending.getBizName());
                        commissionRateDTO.setMerchantId(pending.getAdminId());
                    }
                }
                data.add(commissionRateDTO);
            });
            pageable.setPages(page.getPages());
            pageable.setTotal(page.getTotal());
            return pageable;
        }
        return PageableUtil.emptyPage(commissionRateQueryPageBO.getPageNum(), commissionRateQueryPageBO.getPageSize());
    }
}
