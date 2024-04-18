package com.love.rbac.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.rbac.bo.SysPermEditBO;
import com.love.rbac.bo.SysPermQueryPageBO;
import com.love.rbac.bo.SysPermSaveBO;
import com.love.rbac.dto.SysPermDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "rbac-service-api", contextId = "sysPermFeignClient", path = "sys/perm")
public interface SysPermFeignClient {

    @PostMapping("save")
    SysPermDTO save(SysPermSaveBO sysPermSaveBO);

    @PostMapping("edit")
    SysPermDTO edit(SysPermEditBO sysPermEditBO);

    @GetMapping("queryById")
    Result<SysPermDTO> queryById(@SpringQueryMap IdParam idParam);

    @GetMapping("deleteById")
    Boolean deleteById(@SpringQueryMap IdParam idParam);

    @GetMapping("page")
    Pageable<SysPermDTO> page(@SpringQueryMap SysPermQueryPageBO sysPermQueryPageBO);

    @GetMapping("tree")
    List<SysPermDTO> tree(@SpringQueryMap IdParam idParam);
}
