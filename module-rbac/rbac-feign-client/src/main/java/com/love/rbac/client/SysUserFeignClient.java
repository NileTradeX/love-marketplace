package com.love.rbac.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.rbac.bo.*;
import com.love.rbac.dto.LoginUserDTO;
import com.love.rbac.dto.SysUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "rbac-service-api", contextId = "sysUserFeignClient", path = "sys/user")
public interface SysUserFeignClient {

    @PostMapping("save")
    SysUserDTO save(SysUserSaveBO sysUserSaveBO);

    @PostMapping("edit")
    SysUserDTO edit(SysUserEditBO sysUserEditBO);

    @GetMapping("queryById")
    SysUserDTO queryById(@SpringQueryMap IdParam idParam);

    @GetMapping("deleteById")
    Boolean deleteById(@SpringQueryMap IdParam idParam);

    @GetMapping("page")
    Pageable<SysUserDTO> page(@SpringQueryMap SysUserQueryPageBO sysUserQueryPageBO);

    @PostMapping("changePassword")
    Boolean changePassword(SysUserChangePasswordBO sysUserChangePasswordBO);

    @PostMapping("login")
    LoginUserDTO login(SysUserLoginBO sysUserLoginBO);
}
