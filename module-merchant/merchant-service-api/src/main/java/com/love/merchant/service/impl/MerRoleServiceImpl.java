package com.love.merchant.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageUtil;
import com.love.merchant.bo.MerRoleEditBO;
import com.love.merchant.bo.MerRoleQueryPageBO;
import com.love.merchant.bo.MerRoleSaveBO;
import com.love.merchant.bo.SettingPermsBO;
import com.love.merchant.dto.MerPermDTO;
import com.love.merchant.dto.MerRoleDTO;
import com.love.merchant.entity.MerRole;
import com.love.merchant.mapper.MerRoleMapper;
import com.love.merchant.service.MerPermService;
import com.love.merchant.service.MerRolePermService;
import com.love.merchant.service.MerRoleService;
import com.love.merchant.service.MerUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerRoleServiceImpl extends ServiceImpl<MerRoleMapper, MerRole> implements MerRoleService {

    private final MerPermService merPermService;
    private final MerRolePermService merRolePermService;
    private final MerUserRoleService merUserRoleService;

    @Override
    public MerRoleDTO save(MerRoleSaveBO merRoleSaveBO) {
        MerRole temp = this.lambdaQuery().eq(MerRole::getName, merRoleSaveBO.getName()).eq(MerRole::getGroupId, merRoleSaveBO.getGroupId()).one();
        if (Objects.nonNull(temp)) {
            throw BizException.build("Roles already exist.");
        }

        MerRole merRole = BeanUtil.copyProperties(merRoleSaveBO, MerRole.class);
        this.save(merRole);
        return BeanUtil.copyProperties(merRole, MerRoleDTO.class);
    }

    @Override
    public MerRoleDTO edit(MerRoleEditBO merRoleEditBO) {
        MerRole merRole = BeanUtil.copyProperties(merRoleEditBO, MerRole.class);
        this.updateById(merRole);
        return BeanUtil.copyProperties(merRole, MerRoleDTO.class);
    }

    @Override
    public MerRoleDTO queryById(IdParam idParam) {
        MerRole merRole = this.getById(idParam.getId());
        if (Objects.isNull(merRole)) {
            throw BizException.build("Roles don't exist.");
        }

        return BeanUtil.copyProperties(merRole, MerRoleDTO.class);
    }

    @Override
    public Boolean deleteById(IdParam idParam) {
        if (merRolePermService.countByRoleId(idParam) > 0) {
            throw BizException.build("this role has attached many perms, cannot delete");
        }

        if (merUserRoleService.countByRoleId(idParam) > 0) {
            throw BizException.build("this role has attached many users, cannot delete");
        }

        return this.removeById(idParam.getId());
    }

    @Override
    public Pageable<MerRoleDTO> page(MerRoleQueryPageBO merRoleQueryPageBO) {
        Page<MerRole> page = this.lambdaQuery()
                .eq(MerRole::getGroupId, merRoleQueryPageBO.getGroupId())
                .page(new Page<>(merRoleQueryPageBO.getPageNum(), merRoleQueryPageBO.getPageSize()));
        page.setRecords(page.getRecords().stream().filter(merRole -> !merRole.getName().contains("admin")).collect(Collectors.toList()));
        return PageUtil.toPage(page, MerRoleDTO.class);
    }

    @Override
    public List<MerPermDTO> queryPerms(Long roleId) {
        return merPermService.tree(roleId, true);
    }

    @Override
    public Boolean settingPerms(SettingPermsBO settingPermsBO) {
        return merRolePermService.saveBatch(settingPermsBO);
    }
}
