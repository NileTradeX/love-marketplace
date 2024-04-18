package com.love.user.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.user.sdk.bo.*;
import com.love.user.sdk.dto.UserDTO;

import java.util.List;


public interface UserService {

    UserDTO save(UserSaveBO userSaveBO);

    UserDTO edit(UserEditBO userEditBO);

    UserDTO queryById(IdParam idParam, boolean simple);

    Boolean deleteById(IdParam idParam);

    Pageable<UserDTO> page(UserQueryPageBO userQueryPageBO);

    UserDTO queryByEmail(UserQueryByEmailBO userQueryByEmailBO);

    UserDTO login(UserLoginBO userLoginBO);

    Boolean changePassword(UserChangePasswordBO userResetPasswordBO);

    Boolean resetPassword(UserResetPasswordBO userResetPasswordBO);

    Boolean verifyEmail(IdParam idParam);

    Boolean changeAvatar(UserChangeAvatarBO userChangeAvatarBO);

    List<UserDTO> queryByIdList(IdsParam idsParam);
}
