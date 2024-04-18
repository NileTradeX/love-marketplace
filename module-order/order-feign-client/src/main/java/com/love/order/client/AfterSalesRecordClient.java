package com.love.order.client;

import com.love.common.page.Pageable;
import com.love.order.bo.*;
import com.love.order.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "order-service-api", contextId = "afterSalesRecordFeignClient", path = "afterSalesRecord")
public interface AfterSalesRecordClient {
    @PostMapping("refund/customer/create")
    AfterSalesRecordDTO customerStartRefund(AfterSalesApplyBO afterSalesApplyBO);

    @PostMapping("refund/customer/close")
    AfterSalesRecordDTO customerCloseRefund(AfterSalesCustomerCancelBO afterSalesCustomerCancelBO);

    @GetMapping("refund/page")
    Pageable<AfterSalesRecordDetailDTO> afterSalesPage(@SpringQueryMap AfterSalesOrderQueryPageBO afterSalesOrderQueryPageBO);

    @GetMapping("refund/detail")
    AfterSalesRecordDetailDTO afterSalesDetail(@SpringQueryMap AfterSalesQueryBO afterSalesOrderQueryPageParam);

    @PostMapping("refund/merchant/agree")
    AfterSalesRecordDTO merchantAgreeRefund(AfterSalesMerchantAgreeBO afterSalesMerchantAgreeBO);

    @PostMapping("refund/merchant/agreeCallBack")
    AfterSalesRecordDTO merchantAgreeRefundCallBack(AfterSalesMerchantAgreeCallBackBO afterSalesMerchantAgreeCallBackBO);

    @PostMapping("refund/merchant/decline")
    AfterSalesRecordDTO merchantDeclineRefund(AfterSalesMerchantRejectBO afterSalesMerchantRejectBO);

    @GetMapping("queryAfterSaleSkuList")
    List<AfterSalesSkuRecordDTO> queryAfterSaleSkuList(@SpringQueryMap AfterSalesRecordQueryBO afterSalesRecordQueryBO);
    @GetMapping("queryLastAfterSaleSkuList")
    List<AfterSalesSkuRecordDTO> queryLastAfterSaleSkuList(@SpringQueryMap AfterSalesRecordQueryBO afterSalesRecordQueryBO);
    @GetMapping("refund/skuList")
    List<AfterSalesSkuDTO> afterSaleSkuList(@SpringQueryMap AfterSalesQueryBO afterSalesQueryBO);

    @GetMapping("refund/thirdList")
    List<OrderRefundDTO> thirdList(@SpringQueryMap AfterSalesQueryBO afterSalesQueryBO);
}
