package com.love.order.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.love.common.page.Pageable;
import com.love.common.result.Result;
import com.love.order.bo.*;
import com.love.order.dto.*;
import com.love.order.entity.AfterSalesRecord;
import com.love.order.entity.AfterSalesSku;
import com.love.order.service.AfterSalesRecordService;
import com.love.order.service.AfterSalesSkuService;
import com.love.order.service.OrderRefundService;
import com.love.order.service.aftersale.AfterSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * (AfterSalesRecord)表控制层
 *
 * @author eric
 * @since 2023-07-11 16:46:13
 */
@RestController
@RequestMapping("afterSalesRecord")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AfterSalesRecordController {

    private final AfterSaleService afterSaleService;
    private final AfterSalesRecordService afterSalesRecordService;
    private final AfterSalesSkuService afterSalesSkuService;
    private final OrderRefundService orderRefundService;

    @PostMapping("refund/customer/create")
    public Result<AfterSalesRecordDTO> refundUserCreate(@RequestBody AfterSalesApplyBO afterSalesApplyBO) {
        return Result.success(afterSaleService.consumeApply(afterSalesApplyBO));
    }

    @PostMapping("refund/customer/close")
    public Result<AfterSalesRecordDTO> refundUserClose(@RequestBody AfterSalesCustomerCancelBO afterSalesCustomerCancelBO) {
        return Result.success(afterSaleService.customerCancel(afterSalesCustomerCancelBO));
    }

    @GetMapping("refund/page")
    public Result<Pageable<AfterSalesRecordDetailDTO>> afterSalesPage(AfterSalesOrderQueryPageBO afterSalesOrderQueryPageBO) {
        return Result.success(afterSalesRecordService.afterSalesPage(afterSalesOrderQueryPageBO));
    }

    @GetMapping("refund/detail")
    public Result<AfterSalesRecordDetailDTO> afterSalesDetail(AfterSalesQueryBO afterSalesOrderQueryPageParam) {
        return Result.success(afterSalesRecordService.afterSalesDetail(afterSalesOrderQueryPageParam));
    }

    @PostMapping("refund/merchant/agree")
    public Result<AfterSalesRecordDTO> merchantAgree(@RequestBody AfterSalesMerchantAgreeBO afterSalesMerchantAgreeBO) {
        return Result.success(afterSaleService.merchantAgree(afterSalesMerchantAgreeBO));
    }

    @PostMapping("refund/merchant/agreeCallBack")
    public Result<AfterSalesRecordDTO> merchantAgreeRefundCallBack(@RequestBody AfterSalesMerchantAgreeCallBackBO afterSalesMerchantAgreeCallBackBO) {
        return Result.success(afterSaleService.merchantAgreeRefundCallBack(afterSalesMerchantAgreeCallBackBO));
    }

    @PostMapping("refund/merchant/decline")
    public Result<AfterSalesRecordDTO> merchantDeclineRefund(@RequestBody AfterSalesMerchantRejectBO afterSalesMerchantRejectBO) {
        return Result.success(afterSaleService.merchantReject(afterSalesMerchantRejectBO));
    }

    @GetMapping("queryAfterSaleSkuList")
    public Result<List<AfterSalesSkuRecordDTO>> queryAfterSaleSkuList(AfterSalesRecordQueryBO bo) {
        List<AfterSalesSku> skuEntityList;
        if (CollectionUtil.isEmpty(bo.getQuerySkuBOList())) {
            skuEntityList = afterSalesRecordService.queryAfterSaleSkuList(bo.getOrderNo(), bo.getMerOrderNo());
        } else {
            skuEntityList = afterSalesRecordService.queryAfterSaleSkuList(bo.getOrderNo(), bo.getMerOrderNo(), bo.getQuerySkuBOList());
        }
        return Result.success(assembleAfterSaleSkuList(bo.getOrderNo(), skuEntityList));
    }

    @GetMapping("queryLastAfterSaleSkuList")
    public Result<List<AfterSalesSkuRecordDTO>> queryLastAfterSaleSkuList(AfterSalesRecordQueryBO bo) {
        List<AfterSalesSku> skuEntityList = afterSalesSkuService.queryLastAfterSaleSkuList(bo.getOrderNo(), bo.getMerOrderNo(), bo.getQuerySkuBOList());
        if (CollectionUtil.isEmpty(skuEntityList)) {
            return Result.success(Lists.newArrayList());
        }

        return Result.success(assembleAfterSaleSkuList(bo.getOrderNo(), skuEntityList));
    }

    private List<AfterSalesSkuRecordDTO> assembleAfterSaleSkuList(String orderNo, List<AfterSalesSku> skuEntityList) {
        List<AfterSalesSkuRecordDTO> afterSalesSkuRecordDTOS = BeanUtil.copyToList(skuEntityList, AfterSalesSkuRecordDTO.class);

        List<AfterSalesRecord> afterSalesRecordList = afterSalesRecordService.queryByOrderNo(orderNo);
        if (CollectionUtil.isEmpty(afterSalesRecordList)) {
            return Lists.newArrayList();
        }
        Map<String, AfterSalesRecord> afterSalesRecordMap = afterSalesRecordList.stream().collect(Collectors.toMap(AfterSalesRecord::getAfterSaleNo, Function.identity()));
        afterSalesSkuRecordDTOS.forEach(t -> {
            t.setAfterSalesRecordDTO(BeanUtil.copyProperties(afterSalesRecordMap.get(t.getAfterSaleNo()), AfterSalesRecordDTO.class));
        });

        return afterSalesSkuRecordDTOS;
    }

    @GetMapping("refund/skuList")
    public Result<List<AfterSalesSkuDTO>> afterSaleSkuList(AfterSalesQueryBO afterSalesQueryBO) {
        return Result.success(afterSalesSkuService.queryAfterSaleSku(afterSalesQueryBO));
    }

    @GetMapping("refund/thirdList")
    public Result<List<OrderRefundDTO>> thirdList(AfterSalesQueryBO afterSalesQueryBO) {
        return Result.success(orderRefundService.thirdList(afterSalesQueryBO));
    }
}