package com.love.influencer.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.influencer.bo.*;
import com.love.influencer.dto.InfUserDTO;
import com.love.influencer.dto.InfUserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "influencer-service-api", contextId = "infUserFeignClient", path = "influencer")
public interface InfUserFeignClient {

    @PostMapping("save")
    InfUserDTO save(InfUserSaveBO infUserSaveBO);

    @PostMapping("edit")
    Boolean edit(InfUserEditBO infUserEditBO);

    @GetMapping("queryById")
    InfUserInfoDTO queryById(@SpringQueryMap IdParam idParam);

    @GetMapping("deleteById")
    Boolean deleteById(@SpringQueryMap IdParam idParam);

    @GetMapping("page")
    Pageable<InfUserDTO> page(@SpringQueryMap InfUserQueryPageBO infUserQueryPageBO);

    @PostMapping("changePassword")
    Boolean changePassword(InfUserChangePasswordBO infUserChangePasswordBO);

    @PostMapping("resetPassword")
    Boolean resetPassword(InfUserResetPasswordBO infUserResetPasswordBO);

    @PostMapping("login")
    InfUserDTO login(InfUserLoginBO infUserLoginBO);

    @PostMapping("changeAvatar")
    Boolean changeAvatar(InfUserChangeAvatarBO infUserChangeAvatarBO);

    @GetMapping("queryByCode")
    InfUserDTO queryByCode(@SpringQueryMap InfUserQueryByCodeBO infUserQueryByCodeBO);

    @GetMapping("updateStatus")
    Boolean updateStatus(@SpringQueryMap InfUserUpdateStatusBO infUserUpdateStatusBO);
}
