package com.love.merchant.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.merchant.bo.*;
import com.love.merchant.dto.*;
import com.love.merchant.entity.MerUser;
import com.love.merchant.entity.MerchantRegInfo;

import java.util.List;

public interface MerUserService {

    MerUserDTO save(MerUserSaveBO merUserSaveBO);

    MerUserDTO saveAdmin(MerUserAdminSaveBO merUserAdminSaveBO);

    MerUserDTO edit(MerUserEditBO merUserEditBO);

    MerUserDTO editAdmin(MerUserAdminSaveBO merUserAdminSaveBO);

    MerUserDTO queryById(IdParam idParam);

    MerUserAdminDTO queryAdminById(MerUserAdminQueryBO idParam);

    Boolean deleteById(IdParam idParam);

    Pageable<MerUserDTO> page(MerUserQueryPageBO merUserQueryPageBO);

    Pageable<MerUserDTO> simpleAdminPage(MerUserQueryAdminPageBO merUserQueryAdminPageBO);

    Pageable<MerUserAdminDTO> fullAdminPage(MerUserQueryAdminPageBO merUserQueryAdminPageBO);

    List<Long> queryApprovedList(MerUserQueryBO merUserQueryBO);

    boolean changePassword(MerUserChangePasswordBO merUserChangePasswordBO);

    boolean resetPassword(MerUserResetPasswordBO merUserResetPasswordBO);

    LoginUserDTO login(MerUserLoginBO merUserLoginBO);

    boolean isAdmin(Long userId);

    long queryGroupId(Long adminId);

    MerUser queryAdminByGroupId(Long groupId);

    MerchantRegInfo queryMerchantRegInfo(String bizName);

    MerUserDTO queryByAccount(MerUserQueryByAccountBO merUserQueryByAccountBO);

    MerUserAdminApproveDTO approve(MerUserAdminApproveBO merUserAdminApproveBO);

    MerUserAdminRejectDTO reject(MerUserAdminRejectBO merUserAdminRejectBO);

    MerUserAdminReviewStatDTO reviewStat(MerUserAdminReviewStatBO merUserAdminReviewStatBO);
}
