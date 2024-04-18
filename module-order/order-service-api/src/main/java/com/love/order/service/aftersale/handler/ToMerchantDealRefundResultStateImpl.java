package com.love.order.service.aftersale.handler;

import com.love.order.entity.AfterSalesRecord;
import com.love.order.enums.AfterSaleState;
import com.love.order.enums.RefundStatus;
import com.love.order.service.AfterSalesRecordService;
import com.love.order.service.aftersale.api.AfterSaleStateApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 待店铺处理退货退款
 *
 * @Auther: lce
 * @Date: 2023/3/22 0022
 */
@Slf4j
@Service("TO_MERCHANT_DEAL_REFUND_RESULT")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ToMerchantDealRefundResultStateImpl implements AfterSaleStateApi {

    private final AfterSalesRecordService afterSalesRecordService;

    public AfterSalesRecord merchantAgreeRefundCallBack(AfterSalesRecord entity , RefundStatus refundStatusEnum , String thirdRefundNo, AfterSaleState currentEnum){
        return afterSalesRecordService.merchantAgreeRefundCallBack(entity , refundStatusEnum , thirdRefundNo);
    }
}
