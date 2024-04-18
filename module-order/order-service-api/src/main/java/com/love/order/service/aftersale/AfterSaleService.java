package com.love.order.service.aftersale;

import cn.hutool.core.bean.BeanUtil;
import com.love.common.exception.BizException;
import com.love.order.bo.*;
import com.love.order.dto.AfterSalesRecordDTO;
import com.love.order.entity.AfterSalesRecord;
import com.love.order.enums.AfterSaleState;
import com.love.order.service.AfterSalesRecordService;
import com.love.order.service.AfterSalesSkuService;
import com.love.order.service.aftersale.api.AfterSaleStateApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/**
 * @Auther: lce
 * @Date: 2023/3/23
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AfterSaleService {

    private final Map<String, AfterSaleStateApi> stateApiMap;
    private final AfterSalesRecordService afterSalesRecordService;
    private final AfterSalesSkuService afterSalesSkuService;

    private ReentrantLock lock = new ReentrantLock();

    @Transactional(rollbackFor = Exception.class)
    public AfterSalesRecord apply(Function<String, AfterSalesRecord> function, String afterSaleNo) {
        try {
            lock.lock();
            return function.apply(afterSaleNo);
        } catch (Exception e) {
            log.error("After sale System exception , after_sale_no:{}", afterSaleNo, e);
            throw BizException.build(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private AfterSaleStateApi getAfterSaleState(AfterSaleState currentEnum) {
        AfterSaleStateApi afterSaleStateApi = stateApiMap.get(currentEnum.name());
        if (afterSaleStateApi == null) {
            throw BizException.build("not support, current status: " + currentEnum.getDesc());
        }
        return afterSaleStateApi;
    }

    private AfterSaleState getCurrentEnum(AfterSalesRecord entity) {
        return AfterSaleState.getEnum(entity.getAfterSaleState());
    }

    public AfterSalesRecordDTO consumeApply(AfterSalesApplyBO applyBO) {
        try {
            lock.lock();
            return BeanUtil.copyProperties(afterSalesRecordService.customerApply(applyBO), AfterSalesRecordDTO.class);
        } catch (Exception e) {
            log.error("After sale System exception , orderId:{}", applyBO.getOrderNo(), e);
            throw BizException.build(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public AfterSalesRecordDTO customerCancel(AfterSalesCustomerCancelBO bo) {
        return BeanUtil.copyProperties(apply(afterSaleNo -> {
            AfterSalesRecord entity = afterSalesRecordService.queryByAfterSaleNo(afterSaleNo);

            AfterSaleState currentEnum = getCurrentEnum(entity);
            if (AfterSaleState.REFUND_SUCCESS.equals(currentEnum) || AfterSaleState.CANCEL.equals(currentEnum)) {
                throw BizException.build("not support this operate，current status : " + currentEnum.getDesc());
            }
            return afterSalesRecordService.customerCancel(entity);
        }, bo.getAfterSaleNo()), AfterSalesRecordDTO.class);
    }

    public AfterSalesRecordDTO merchantAgree(AfterSalesMerchantAgreeBO bo) {
        return BeanUtil.copyProperties(apply(afterSaleNo -> {
            AfterSalesRecord entity = afterSalesRecordService.queryByAfterSaleNo(afterSaleNo);

            AfterSaleState currentEnum = getCurrentEnum(entity);
            return getAfterSaleState(currentEnum).merchantAgree(entity, currentEnum);
        }, bo.getAfterSaleNo()), AfterSalesRecordDTO.class);
    }

    public AfterSalesRecordDTO merchantAgreeRefundCallBack(AfterSalesMerchantAgreeCallBackBO bo) {
        return BeanUtil.copyProperties(apply(afterSaleNo -> {
            AfterSalesRecord entity = afterSalesRecordService.queryByAfterSaleNo(afterSaleNo);

            AfterSaleState currentEnum = getCurrentEnum(entity);
            return getAfterSaleState(currentEnum).merchantAgreeRefundCallBack(entity, bo.getRefundStatus(), bo.getThirdRefundNo(), currentEnum);
        }, bo.getAfterSaleNo()), AfterSalesRecordDTO.class);
    }

    public AfterSalesRecordDTO merchantReject(AfterSalesMerchantRejectBO bo) {
        return BeanUtil.copyProperties(apply(afterSaleNo -> {
            AfterSalesRecord entity = afterSalesRecordService.queryByAfterSaleNo(afterSaleNo);
            entity.setMerchantDealDesc(bo.getAfterSaleRemark());

            AfterSaleState currentEnum = getCurrentEnum(entity);
            return getAfterSaleState(currentEnum).merchantReject(entity, currentEnum);
        }, bo.getAfterSaleNo()), AfterSalesRecordDTO.class);
    }
}
