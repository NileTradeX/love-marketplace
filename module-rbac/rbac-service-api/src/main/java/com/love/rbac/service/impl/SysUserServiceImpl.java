package com.love.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageUtil;
import com.love.common.util.PostProcessor;
import com.love.rbac.bo.*;
import com.love.rbac.dto.LoginUserDTO;
import com.love.rbac.dto.SysPermDTO;
import com.love.rbac.dto.SysRoleDTO;
import com.love.rbac.dto.SysUserDTO;
import com.love.rbac.entity.SysUser;
import com.love.rbac.entity.SysUserRole;
import com.love.rbac.enums.SysUserType;
import com.love.rbac.mapper.SysUserMapper;
import com.love.rbac.service.SysPermService;
import com.love.rbac.service.SysRoleService;
import com.love.rbac.service.SysUserRoleService;
import com.love.rbac.service.SysUserService;
import com.love.rbac.util.BCryptUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService, PostProcessor<SysUser, SysUserDTO> {
    private final SysPermService sysPermService;
    private final SysRoleService sysRoleService;
    private final SysUserRoleService sysUserRoleService;

    @Override
    public void process(SysUser src, SysUserDTO dst) {
        SysUserRole sysUserRole = sysUserRoleService.queryByUserId(src.getId());
        if (Objects.nonNull(sysUserRole)) {
            long roleId = sysUserRole.getRoleId();
            dst.setRoleId(roleId);
            SysRoleDTO sysRole = sysRoleService.queryById(IdParam.builder().id(roleId).build());
            dst.setRoleName(sysRole.getName());
        }
        dst.setSuper(src.getType() == SysUserType.SUPER.getType());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysUserDTO save(SysUserSaveBO sysUserSaveBO) {
        SysUser temp = this.lambdaQuery().eq(SysUser::getAccount, sysUserSaveBO.getAccount()).one();
        if (Objects.nonNull(temp)) {
            throw BizException.build("Account already exists.");
        }

        SysUser sysUser = BeanUtil.copyProperties(sysUserSaveBO, SysUser.class);
        sysUser.setPassword(BCryptUtils.encode(sysUser.getPassword()));
        sysUser.setType(SysUserType.NORMAL.getType());
        sysUser.setUid("S" + IdWorker.getIdStr());

        boolean result = this.save(sysUser);
        if (result) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getId());
            sysUserRole.setRoleId(sysUserSaveBO.getRoleId());
            sysUserRoleService.save(sysUserRole);
        }
        return BeanUtil.copyProperties(sysUser, SysUserDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserDTO edit(SysUserEditBO sysUserEditBO) {
        SysUser sysUser = BeanUtil.copyProperties(sysUserEditBO, SysUser.class);
        this.updateById(sysUser);
        long userId = sysUserEditBO.getId();
        Long roleId = sysUserEditBO.getRoleId();
        if (Objects.nonNull(roleId)) {
            sysUser = this.getById(userId);
            SysUserRole sysUserRole = sysUserRoleService.queryByUserId(userId);
            if (Objects.nonNull(sysUserRole) && sysUserRole.getRoleId().longValue() != roleId) {
                sysUserRoleService.deleteByUserId(userId);
                SysUserRole temp = new SysUserRole();
                temp.setRoleId(roleId);
                temp.setUserId(userId);
                sysUserRoleService.save(temp);
            }
        }
        return BeanUtil.copyProperties(sysUser, SysUserDTO.class);
    }


    @Override
    public SysUserDTO queryById(IdParam idParam) {
        SysUser sysUser = this.getById(idParam.getId());
        if (Objects.isNull(sysUser)) {
            throw BizException.build("User does not exist.");
        }

        SysUserDTO sysUserDTO = BeanUtil.copyProperties(sysUser, SysUserDTO.class);
        process(sysUser, sysUserDTO);
        return sysUserDTO;
    }

    @Override
    public Boolean deleteById(IdParam idParam) {
        boolean result = this.removeById(idParam.getId());
        return result && sysUserRoleService.deleteByUserId(idParam.getId());
    }

    @Override
    public Pageable<SysUserDTO> page(SysUserQueryPageBO sysUserQueryPageBO) {
        Page<SysUser> page = this.lambdaQuery()
                .eq(SysUser::getType, SysUserType.NORMAL.getType())
                .like(StringUtils.isNotBlank(sysUserQueryPageBO.getAccount()), SysUser::getAccount, sysUserQueryPageBO.getAccount())
                .like(StringUtils.isNotBlank(sysUserQueryPageBO.getUsername()), SysUser::getUsername, sysUserQueryPageBO.getUsername())
                .page(new Page<>(sysUserQueryPageBO.getPageNum(), sysUserQueryPageBO.getPageSize()));
        return PageUtil.toPage(page, SysUserDTO.class, this);
    }

    @Override
    public Boolean changePassword(SysUserChangePasswordBO sysUserChangePasswordBO) {
        return this.lambdaUpdate().set(SysUser::getPassword, BCryptUtils.encode(sysUserChangePasswordBO.getPassword())).eq(SysUser::getId, sysUserChangePasswordBO.getId()).update();
    }

    @Override
    public LoginUserDTO login(SysUserLoginBO sysUserLoginBO) {
        SysUser sysUser = this.lambdaQuery().eq(SysUser::getAccount, sysUserLoginBO.getAccount()).one();
        if (Objects.isNull(sysUser) || Objects.isNull(sysUser.getId())) {
            throw BizException.build("Account : %s does not exist.", sysUserLoginBO.getAccount());
        }

        if (!BCryptUtils.matches(sysUserLoginBO.getPassword(), sysUser.getPassword())) {
            throw BizException.build("Authentication failed. Please check your password and try again.");
        }

        List<SysPermDTO> sysPermDTOS;
        if (SysUserType.SUPER.getType() == sysUser.getType()) {
            sysPermDTOS = sysPermService.tree(null, true);
        } else {
            SysUserRole sysUserRole = sysUserRoleService.queryByUserId(sysUser.getId());
            if (Objects.nonNull(sysUserRole)) {
                sysPermDTOS = sysPermService.tree(sysUserRole.getRoleId(), false);
            } else {
                sysPermDTOS = new ArrayList<>();
            }
        }
        sysUser.setLastLoginTime(LocalDateTime.now());
        this.updateById(sysUser);
        SysUserDTO sysUserDTO = BeanUtil.copyProperties(sysUser, SysUserDTO.class);
        this.process(sysUser, sysUserDTO);
        return LoginUserDTO.builder().user(sysUserDTO).perms(sysPermDTOS).build();
    }

    @Override
    public Boolean isSuper(Long userId) {
        if (Objects.isNull(userId)) {
            return false;
        }
        SysUser sysUser = this.getById(userId);
        return Objects.nonNull(sysUser) && sysUser.getType() == SysUserType.SUPER.getType();
    }
}
