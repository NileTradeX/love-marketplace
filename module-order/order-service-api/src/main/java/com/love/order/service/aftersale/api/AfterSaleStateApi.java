package com.love.order.service.aftersale.api;

import com.love.common.exception.BizException;
import com.love.order.entity.AfterSalesRecord;
import com.love.order.enums.AfterSaleState;
import com.love.order.enums.RefundStatus;

/**
 * @Auther: lce
 * @Date: 2023/3/22 0022
 */
public interface AfterSaleStateApi {
    default AfterSalesRecord merchantAgree(AfterSalesRecord entity , AfterSaleState currentEnum){
        throw BizException.build("not support this operate，current status : " + currentEnum.getDesc());
    }
    default AfterSalesRecord merchantAgreeRefundCallBack(AfterSalesRecord entity , RefundStatus refundStatusEnum , String thirdRefundNo, AfterSaleState currentEnum){
        throw BizException.build("not support this operate，current status : " + currentEnum.getDesc());
    }
    default AfterSalesRecord merchantReject(AfterSalesRecord entity , AfterSaleState currentEnum){
        throw BizException.build("not support this operate，current status : " + currentEnum.getDesc());
    }

}
