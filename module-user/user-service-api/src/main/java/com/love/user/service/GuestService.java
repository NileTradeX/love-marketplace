package com.love.user.service;

import com.love.common.param.IdsParam;
import com.love.user.sdk.bo.GuestBO;
import com.love.user.sdk.dto.GuestDTO;

import java.util.List;

public interface GuestService {

    GuestDTO saveOrUpdate(GuestBO guestBO);

    GuestDTO queryByEmail(GuestBO guestBO);

    List<GuestDTO> queryByIdList(IdsParam idsParam);
}
