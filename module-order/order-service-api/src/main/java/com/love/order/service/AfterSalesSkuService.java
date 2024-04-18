package com.love.order.service;

import com.love.order.bo.AfterSalesQueryBO;
import com.love.order.bo.AfterSalesRecordQuerySkuBO;
import com.love.order.dto.AfterSalesSkuDTO;
import com.love.order.entity.AfterSalesSku;

import java.util.List;

/**
 * (AfterSalesSku)表服务接口
 *
 * @author eric
 * @since 2023-07-11 16:58:28
 */
public interface AfterSalesSkuService {

    boolean save(AfterSalesSku entity);

    List<AfterSalesSku> queryAfterSaleSkuList(List<String> afterSaleNoList);
    List<AfterSalesSku> queryLastAfterSaleSkuList(String orderNo, String merOrderNo , List<AfterSalesRecordQuerySkuBO> querySkuBOList);

    boolean saveSkuBatch(List<AfterSalesSku> skuEntityList);

    List<AfterSalesSkuDTO> queryAfterSaleSku(AfterSalesQueryBO afterSalesQueryBO);
}

