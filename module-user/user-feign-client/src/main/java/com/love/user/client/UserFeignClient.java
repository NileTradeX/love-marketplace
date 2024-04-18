package com.love.user.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.user.sdk.bo.*;
import com.love.user.sdk.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "user-service-api", contextId = "userFeignClient", path = "user")
public interface UserFeignClient {

    @PostMapping("save")
    UserDTO save(UserSaveBO userSaveBO);

    @PostMapping("edit")
    UserDTO edit(UserEditBO userEditBO);

    @GetMapping("queryById")
    UserDTO detail(@SpringQueryMap IdParam idParam);

    @GetMapping("simple")
    UserDTO simple(@SpringQueryMap IdParam idParam);

    @GetMapping("deleteById")
    Boolean deleteById(@SpringQueryMap IdParam idParam);

    @GetMapping("page")
    Pageable<UserDTO> page(@SpringQueryMap UserQueryPageBO userQueryPageBO);

    @GetMapping("queryByEmail")
    UserDTO queryByEmail(@SpringQueryMap UserQueryByEmailBO userQueryByEmailBO);

    @PostMapping("login")
    UserDTO login(UserLoginBO userLoginBO);

    @PostMapping("changePassword")
    Boolean changePassword(UserChangePasswordBO userChangePasswordBO);

    @PostMapping("resetPassword")
    Boolean resetPassword(UserResetPasswordBO userResetPasswordBO);

    @GetMapping("verifyEmail")
    Boolean verifyEmail(@SpringQueryMap IdParam idParam);

    @PostMapping("changeAvatar")
    Boolean changeAvatar(UserChangeAvatarBO userChangeAvatarBO);

    @PostMapping("queryByIdList")
    List<UserDTO> queryByIdList(IdsParam idsParam);
}
