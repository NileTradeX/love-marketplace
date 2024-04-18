package com.love.influencer.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.influencer.bo.*;
import com.love.influencer.dto.InfUserDTO;
import com.love.influencer.dto.InfUserInfoDTO;
import com.love.influencer.entity.InfUser;

public interface InfUserService {

    InfUserDTO save(InfUserSaveBO infUserSaveBO);

    Boolean edit(InfUserEditBO infUserEditBO);

    InfUserInfoDTO queryById(IdParam idParam);

    Boolean deleteById(IdParam idParam);

    Pageable<InfUserDTO> page(InfUserQueryPageBO infUserQueryPageBO);

    Boolean changePassword(InfUserChangePasswordBO infUserChangePasswordBO);

    Boolean resetPassword(InfUserResetPasswordBO infUserResetPasswordBO);

    InfUserDTO login(InfUserLoginBO infUserLoginBO);

    Boolean changeAvatar(InfUserChangeAvatarBO infUserChangeAvatarBO);

    InfUser queryInfluencerById(IdParam idParam);

    InfUserDTO queryByCode(InfUserQueryByCodeBO infUserQueryByCodeBO);

    Boolean updateStatus(InfUserUpdateStatusBO infUserUpdateStatusBO);
}
