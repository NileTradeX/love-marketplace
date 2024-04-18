package com.love.merchant.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.merchant.bo.*;
import com.love.merchant.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "merchant-service-api", contextId = "merchantUserFeignClient", path = "mer/user")
public interface MerchantUserFeignClient {

    @PostMapping("save")
    MerUserDTO save(MerUserSaveBO merUserSaveBO);

    @PostMapping("saveAdmin")
    MerUserDTO saveAdmin(MerUserAdminSaveBO merUserAdminSaveBO);

    @PostMapping("edit")
    MerUserDTO edit(MerUserEditBO merUserEditBO);

    @PostMapping("editAdmin")
    MerUserDTO editAdmin(MerUserAdminSaveBO merUserAdminSaveBO);

    @GetMapping("queryById")
    MerUserDTO queryById(@SpringQueryMap IdParam idParam);

    @GetMapping("queryAdminById")
    MerUserAdminDTO queryAdminById(@SpringQueryMap MerUserAdminQueryBO merUserAdminQueryBO);

    @GetMapping("deleteById")
    Boolean deleteById(@SpringQueryMap IdParam idParam);

    @GetMapping("page")
    Pageable<MerUserDTO> page(@SpringQueryMap MerUserQueryPageBO merUserQueryPageBO);

    @GetMapping("simpleAdminPage")
    Pageable<MerUserDTO> simpleAdminPage(@SpringQueryMap MerUserQueryAdminPageBO merUserQueryAdminPageBO);

    @GetMapping("fullAdminPage")
    Pageable<MerUserAdminDTO> fullAdminPage(@SpringQueryMap MerUserQueryAdminPageBO merUserQueryAdminPageBO);

    @PostMapping("queryByAccount")
    MerUserDTO queryByAccount(MerUserQueryByAccountBO merUserQueryByAccountBO);

    @PostMapping("changePassword")
    Boolean changePassword(MerUserChangePasswordBO merUserChangePasswordBO);

    @PostMapping("resetPassword")
    Boolean resetPassword(MerUserResetPasswordBO merUserResetPasswordBO);

    @PostMapping("login")
    LoginUserDTO login(MerUserLoginBO merUserLoginBO);

    @PostMapping("approve")
    MerUserAdminApproveDTO approve(MerUserAdminApproveBO merUserAdminApproveBO);

    @PostMapping("reject")
    MerUserAdminRejectDTO reject(MerUserAdminRejectBO merUserAdminRejectBO);

    @GetMapping("review/stat")
    MerUserAdminReviewStatDTO reviewStat(@SpringQueryMap MerUserAdminReviewStatBO merUserAdminReviewStatBO);
}
