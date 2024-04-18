package com.love.rbac.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.rbac.bo.SettingPermsBO;
import com.love.rbac.bo.SysRoleEditBO;
import com.love.rbac.bo.SysRoleQueryPageBO;
import com.love.rbac.bo.SysRoleSaveBO;
import com.love.rbac.dto.SysPermDTO;
import com.love.rbac.dto.SysRoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "rbac-service-api", contextId = "sysRoleFeignClient", path = "sys/role")
public interface SysRoleFeignClient {

    @PostMapping("save")
    SysRoleDTO save(SysRoleSaveBO sysRoleSaveBO);

    @PostMapping("edit")
    SysRoleDTO edit(SysRoleEditBO sysRoleEditBO);

    @GetMapping("queryById")
    SysRoleDTO queryById(@SpringQueryMap IdParam idParam);

    @GetMapping("deleteById")
    Boolean deleteById(@SpringQueryMap IdParam idParam);

    @GetMapping("page")
    Pageable<SysRoleDTO> page(@SpringQueryMap SysRoleQueryPageBO sysRoleQueryPageBO);

    @GetMapping("queryPerms")
    List<SysPermDTO> queryPerms(@RequestParam(value = "roleId", required = false) Long roleId);

    @PostMapping("settingPerms")
    Boolean settingPerms(SettingPermsBO settingPermsBO);
}
