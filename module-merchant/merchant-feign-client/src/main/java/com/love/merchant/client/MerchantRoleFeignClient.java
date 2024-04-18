package com.love.merchant.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.merchant.bo.MerRoleEditBO;
import com.love.merchant.bo.MerRoleQueryPageBO;
import com.love.merchant.bo.MerRoleSaveBO;
import com.love.merchant.bo.SettingPermsBO;
import com.love.merchant.dto.MerPermDTO;
import com.love.merchant.dto.MerRoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "merchant-service-api", contextId = "merchantRoleFeignClient", path = "mer/role")
public interface MerchantRoleFeignClient {

    @PostMapping("save")
    MerRoleDTO save(MerRoleSaveBO sysRoleSaveBO);

    @PostMapping("edit")
    MerRoleDTO edit(MerRoleEditBO sysRoleEditBO);

    @GetMapping("queryById")
    MerRoleDTO queryById(@SpringQueryMap IdParam idParam);

    @GetMapping("deleteById")
    Boolean deleteById(@SpringQueryMap IdParam idParam);

    @GetMapping("page")
    Pageable<MerRoleDTO> page(@SpringQueryMap MerRoleQueryPageBO merRoleQueryPageBO);

    @GetMapping("queryPerms")
    List<MerPermDTO> queryPerms(@RequestParam(value = "roleId", required = false) Long roleId);

    @PostMapping("settingPerms")
    Boolean settingPerms(SettingPermsBO settingPermsBO);
}
