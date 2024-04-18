package com.love.merchant.backend.manager;

import com.love.common.exception.BizException;
import com.love.merchant.bo.MerUserAdminQueryBO;
import com.love.merchant.client.MerchantUserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerchantIdManager {

    private final MerchantUserFeignClient merchantUserFeignClient;

    public Long getMerchantId(Long userId) {
        return Optional.ofNullable(merchantUserFeignClient.queryAdminById(MerUserAdminQueryBO.builder()
                .userId(userId).simple(true).build()))
                .orElseThrow(() -> BizException.build("merchant does not exist")).getId();
    }
}
