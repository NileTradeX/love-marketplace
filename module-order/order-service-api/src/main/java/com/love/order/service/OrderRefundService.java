package com.love.order.service;

import com.love.order.bo.AfterSalesQueryBO;
import com.love.order.bo.OrderRefundBO;
import com.love.order.dto.OrderRefundDTO;
import com.love.order.entity.OrderRefundEntity;

import java.util.List;

/**
 * (OrderRefund)表服务接口
 *
 * @author eric
 * @since 2023-07-11 16:59:23
 */
public interface OrderRefundService {

    boolean save(OrderRefundEntity entity);

    OrderRefundEntity refund(OrderRefundBO refundBO);

    List<OrderRefundDTO> thirdList(AfterSalesQueryBO afterSalesQueryBO);
}

