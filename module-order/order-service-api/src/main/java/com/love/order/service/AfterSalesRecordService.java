package com.love.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.love.common.page.Pageable;
import com.love.order.bo.AfterSalesApplyBO;
import com.love.order.bo.AfterSalesOrderQueryPageBO;
import com.love.order.bo.AfterSalesQueryBO;
import com.love.order.bo.AfterSalesRecordQuerySkuBO;
import com.love.order.dto.AfterSalesRecordDetailDTO;
import com.love.order.entity.AfterSalesRecord;
import com.love.order.entity.AfterSalesSku;
import com.love.order.enums.RefundStatus;

import java.util.List;

/**
 * (AfterSalesRecord)表服务接口
 *
 * @author eric
 * @since 2023-07-11 16:46:15
 */
public interface AfterSalesRecordService {

    boolean save(AfterSalesRecord afterSales);

    AfterSalesRecord queryByAfterSaleNo(String afterSaleNo);

    List<AfterSalesRecord> queryByOrderNo(String orderNo);

    AfterSalesRecord customerApply(AfterSalesApplyBO applyBO);

    AfterSalesRecord customerCancel(AfterSalesRecord applyParam);


    AfterSalesRecord merchantReject(AfterSalesRecord entity);

    List<AfterSalesSku> queryAfterSaleSkuList(String orderNo, String merOrderNo);

    List<AfterSalesSku> queryAfterSaleSkuList(String orderNo, String merOrderNo, List<AfterSalesRecordQuerySkuBO> querySkuBOList);

    Page<AfterSalesRecord> page(AfterSalesOrderQueryPageBO afterSalesOrderQueryPageBO);

     AfterSalesRecord merchantAgree(AfterSalesRecord entity);
     AfterSalesRecord merchantAgreeRefundCallBack(AfterSalesRecord entity , RefundStatus refundStatusEnum , String thirdRefundNo);
    Pageable<AfterSalesRecordDetailDTO> afterSalesPage(AfterSalesOrderQueryPageBO afterSalesOrderQueryPageBO);
    AfterSalesRecordDetailDTO afterSalesDetail(AfterSalesQueryBO afterSalesOrderQueryPageParam);
    List<AfterSalesRecordDetailDTO> queryAfterSaleRecordList(String merOrderNo);
}

