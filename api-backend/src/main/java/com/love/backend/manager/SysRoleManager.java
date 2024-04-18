package com.love.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.backend.model.param.SettingPermsParam;
import com.love.backend.model.param.SysRoleEditParam;
import com.love.backend.model.param.SysRoleSaveParam;
import com.love.backend.model.vo.PermVO;
import com.love.backend.model.vo.SysRoleVO;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.PageParam;
import com.love.common.util.PageableUtil;
import com.love.rbac.bo.SettingPermsBO;
import com.love.rbac.bo.SysRoleEditBO;
import com.love.rbac.bo.SysRoleQueryPageBO;
import com.love.rbac.bo.SysRoleSaveBO;
import com.love.rbac.client.SysRoleFeignClient;
import com.love.rbac.dto.SysPermDTO;
import com.love.rbac.dto.SysRoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysRoleManager {

    private final SysRoleFeignClient sysRoleFeignClient;

    public SysRoleVO save(SysRoleSaveParam sysRoleSaveParam) {
        SysRoleSaveBO sysRoleSaveBO = BeanUtil.copyProperties(sysRoleSaveParam, SysRoleSaveBO.class);
        SysRoleDTO sysRoleDTO = sysRoleFeignClient.save(sysRoleSaveBO);
        return BeanUtil.copyProperties(sysRoleDTO, SysRoleVO.class);
    }

    public SysRoleVO edit(SysRoleEditParam sysRoleEditParam) {
        SysRoleEditBO sysRoleEditBO = BeanUtil.copyProperties(sysRoleEditParam, SysRoleEditBO.class);
        SysRoleDTO sysRoleDTO = sysRoleFeignClient.edit(sysRoleEditBO);
        return BeanUtil.copyProperties(sysRoleDTO, SysRoleVO.class);
    }

    public Boolean deleteById(IdParam idParam) {
        return sysRoleFeignClient.deleteById(idParam);
    }

    public Pageable<SysRoleVO> page(PageParam pageParam) {
        SysRoleQueryPageBO sysRoleQueryPageBO = BeanUtil.copyProperties(pageParam, SysRoleQueryPageBO.class);
        Pageable<SysRoleDTO> pageable = sysRoleFeignClient.page(sysRoleQueryPageBO);
        return PageableUtil.toPage(pageable, SysRoleVO.class);
    }

    public List<PermVO> queryPerms(IdParam idParam) {
        List<SysPermDTO> list = sysRoleFeignClient.queryPerms(idParam.getId());
        if (Objects.isNull(list)) {
            return Collections.emptyList();
        }
        return BeanUtil.copyToList(list, PermVO.class);
    }

    public Boolean settingPerms(SettingPermsParam settingPermsParam) {
        SettingPermsBO settingPermsBO = new SettingPermsBO();
        settingPermsBO.setPermIds(settingPermsParam.getPermIds());
        settingPermsBO.setRoleId(settingPermsParam.getRoleId());
        return sysRoleFeignClient.settingPerms(settingPermsBO);
    }
}
