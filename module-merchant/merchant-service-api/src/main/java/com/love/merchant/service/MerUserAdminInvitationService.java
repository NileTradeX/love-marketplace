package com.love.merchant.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.merchant.bo.MerUserAdminInvitationEditBO;
import com.love.merchant.bo.MerUserAdminInvitationQueryByCodeBO;
import com.love.merchant.bo.MerUserAdminInvitationQueryPageBO;
import com.love.merchant.bo.MerUserAdminInvitationSaveBO;
import com.love.merchant.dto.MerUserAdminInvitationDTO;
import com.love.merchant.entity.MerUserAdminInvitation;

public interface MerUserAdminInvitationService {
    MerUserAdminInvitationDTO save(MerUserAdminInvitationSaveBO merUserAdminInvitationSaveBO);

    boolean edit(MerUserAdminInvitationEditBO merUserAdminInvitationEditBO);

    MerUserAdminInvitationDTO queryById(IdParam idParam);

    Pageable<MerUserAdminInvitationDTO> page(MerUserAdminInvitationQueryPageBO merUserAdminInvitationQueryPageBO);

    MerUserAdminInvitationDTO queryByCode(MerUserAdminInvitationQueryByCodeBO merUserAdminInvitationQueryByCodeBO);

    boolean updateByBizName(MerUserAdminInvitation userAdminInvitation);

    MerUserAdminInvitation queryByGroupById(Long groupId);
}
