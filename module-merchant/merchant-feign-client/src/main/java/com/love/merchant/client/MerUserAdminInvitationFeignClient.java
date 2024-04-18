package com.love.merchant.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.merchant.bo.MerUserAdminInvitationEditBO;
import com.love.merchant.bo.MerUserAdminInvitationQueryByCodeBO;
import com.love.merchant.bo.MerUserAdminInvitationQueryPageBO;
import com.love.merchant.bo.MerUserAdminInvitationSaveBO;
import com.love.merchant.dto.MerUserAdminInvitationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "merchant-service-api", contextId = "merUserInvitationFeignClient", path = "mer/user/invitation")
public interface MerUserAdminInvitationFeignClient {

    @PostMapping("save")
    MerUserAdminInvitationDTO save(MerUserAdminInvitationSaveBO merUserAdminInvitationSaveBO);

    @PostMapping("edit")
    Boolean edit(MerUserAdminInvitationEditBO merUserAdminInvitationEditBO);

    @GetMapping("queryById")
    MerUserAdminInvitationDTO detail(@SpringQueryMap IdParam idParam);

    @GetMapping("page")
    Pageable<MerUserAdminInvitationDTO> page(@SpringQueryMap MerUserAdminInvitationQueryPageBO merUserAdminInvitationQueryPageBO);

    @GetMapping("code")
    MerUserAdminInvitationDTO queryByCode(@SpringQueryMap MerUserAdminInvitationQueryByCodeBO merUserAdminInvitationQueryByCodeBO);
}
