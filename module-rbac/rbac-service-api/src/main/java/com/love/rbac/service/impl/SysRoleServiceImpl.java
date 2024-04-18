package com.love.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageUtil;
import com.love.rbac.bo.SettingPermsBO;
import com.love.rbac.bo.SysRoleEditBO;
import com.love.rbac.bo.SysRoleQueryPageBO;
import com.love.rbac.bo.SysRoleSaveBO;
import com.love.rbac.dto.SysPermDTO;
import com.love.rbac.dto.SysRoleDTO;
import com.love.rbac.entity.SysRole;
import com.love.rbac.mapper.SysRoleMapper;
import com.love.rbac.service.SysPermService;
import com.love.rbac.service.SysRolePermService;
import com.love.rbac.service.SysRoleService;
import com.love.rbac.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysPermService sysPermService;
    private final SysRolePermService sysRolePermService;
    private final SysUserRoleService sysUserRoleService;

    @Override
    public SysRoleDTO save(SysRoleSaveBO sysRoleSaveBO) {
        SysRole temp = this.lambdaQuery().eq(SysRole::getName, sysRoleSaveBO.getName()).one();
        if (Objects.nonNull(temp)) {
            throw BizException.build("Role already exist.");
        }

        SysRole sysRole = BeanUtil.copyProperties(sysRoleSaveBO, SysRole.class);
        this.save(sysRole);
        return BeanUtil.copyProperties(sysRole, SysRoleDTO.class);
    }

    @Override
    public SysRoleDTO edit(SysRoleEditBO sysRoleEditBO) {
        SysRole sysRole = BeanUtil.copyProperties(sysRoleEditBO, SysRole.class);
        this.updateById(sysRole);
        return BeanUtil.copyProperties(sysRole, SysRoleDTO.class);
    }

    @Override
    public SysRoleDTO queryById(IdParam idParam) {
        SysRole sysRole = this.getById(idParam.getId());
        if (Objects.isNull(sysRole)) {
            throw BizException.build("Role don't exist.");
        }

        return BeanUtil.copyProperties(sysRole, SysRoleDTO.class);
    }

    @Override
    public Boolean deleteById(IdParam idParam) {
        if (sysRolePermService.countByRoleId(idParam) > 0) {
            throw BizException.build("This role has attached many perms, cannot delete!");
        }

        if (sysUserRoleService.countByRoleId(idParam) > 0) {
            throw BizException.build("This role has attached many users, cannot delete!");
        }

        return this.removeById(idParam.getId());
    }

    @Override
    public Pageable<SysRoleDTO> page(SysRoleQueryPageBO sysRoleQueryPageBO) {
        Page<SysRole> page = this.lambdaQuery().page(new Page<>(sysRoleQueryPageBO.getPageNum(), sysRoleQueryPageBO.getPageSize()));
        return PageUtil.toPage(page, SysRoleDTO.class);
    }

    @Override
    public List<SysPermDTO> queryPerms(Long roleId) {
        return sysPermService.tree(roleId, true);
    }

    @Override
    public Boolean settingPerms(SettingPermsBO settingPermsBO) {
        return sysRolePermService.saveBatch(settingPermsBO);
    }
}
