package com.love.rbac.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.rbac.bo.SysPermEditBO;
import com.love.rbac.bo.SysPermQueryPageBO;
import com.love.rbac.bo.SysPermSaveBO;
import com.love.rbac.dto.SysPermDTO;

import java.util.List;

public interface SysPermService {

    SysPermDTO save(SysPermSaveBO sysPermSaveBO);

    SysPermDTO edit(SysPermEditBO sysPermEditBO);

    SysPermDTO queryById(IdParam idParam);

    Boolean deleteById(IdParam idParam);

    Pageable<SysPermDTO> page(SysPermQueryPageBO sysPermQueryPageBO);

    List<SysPermDTO> tree(Long roleId, boolean isSuper);
}
