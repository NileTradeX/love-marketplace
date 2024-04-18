package com.love.influencer.service;

import com.love.common.page.Pageable;
import com.love.influencer.bo.DashboardQueryBO;
import com.love.influencer.bo.InfUserOrderQueryPageBO;
import com.love.influencer.bo.InfUserOrderRefundBO;
import com.love.influencer.bo.InfUserOrderSaveBO;
import com.love.influencer.dto.DashboardDTO;
import com.love.influencer.dto.InfUserOrderDTO;

public interface InfUserOrderService {

    Boolean save(InfUserOrderSaveBO infUserOrderSaveBO);

    Pageable<InfUserOrderDTO> page(InfUserOrderQueryPageBO infUserOrderQueryPageBO);

    DashboardDTO dashboard(DashboardQueryBO dashboardQueryBO);

    Boolean refund(InfUserOrderRefundBO infUserOrderRefundBO);
}
