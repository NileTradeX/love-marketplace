package com.love.order.service;

import com.love.common.page.Pageable;
import com.love.order.bo.UserQueryOrderPageBO;
import com.love.order.dto.MerchantOrderDTO;
import com.love.order.entity.MerchantOrder;

import java.util.List;

public interface MerchantOrderService {
    boolean save(MerchantOrder merchantOrder);

    List<MerchantOrder> queryByOrderId(Long orderId);

    boolean updateStatusByOrderId(Long orderId, Integer status, List<Long> skuIdList, String reason);

    boolean updateStatusById(Long id, Integer status, String reason);

    long countByStatus(Long merchantId, int status);

    MerchantOrderDTO queryById(Long merchantOrderId);

    List<MerchantOrder> queryByIdList(List<Long> merchantOrderidList);

    boolean updateStatusByOrderNo(String merOrderNo, Integer status, String reason);

    MerchantOrderDTO queryByMerOrderNo(String merOrderNo);

    List<MerchantOrder> queryByNoList(List<String> merchantOrderNoList);

    Pageable<MerchantOrderDTO> queryUserPage(UserQueryOrderPageBO userQueryOrderPageBO);

    boolean migrateGuestOrder(Long guestId,Long userId);

}
