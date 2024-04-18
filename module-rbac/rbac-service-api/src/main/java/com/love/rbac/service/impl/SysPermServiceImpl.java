package com.love.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageUtil;
import com.love.rbac.bo.SysPermEditBO;
import com.love.rbac.bo.SysPermQueryPageBO;
import com.love.rbac.bo.SysPermSaveBO;
import com.love.rbac.dto.SysPermDTO;
import com.love.rbac.entity.SysPerm;
import com.love.rbac.entity.SysRolePerm;
import com.love.rbac.mapper.SysPermMapper;
import com.love.rbac.service.SysPermService;
import com.love.rbac.service.SysRolePermService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysPermServiceImpl extends ServiceImpl<SysPermMapper, SysPerm> implements SysPermService {

    private final SysRolePermService sysRolePermService;

    @Override
    public SysPermDTO save(SysPermSaveBO sysPermSaveBO) {
        SysPerm temp = this.lambdaQuery().eq(SysPerm::getTitle, sysPermSaveBO.getTitle()).eq(SysPerm::getPid, sysPermSaveBO.getPid()).one();
        if (Objects.nonNull(temp)) {
            throw BizException.build("perm already exists.");
        }

        SysPerm sysPerm = BeanUtil.copyProperties(sysPermSaveBO, SysPerm.class);
        super.save(sysPerm);

        return BeanUtil.copyProperties(sysPerm, SysPermDTO.class);
    }

    @Override
    public SysPermDTO edit(SysPermEditBO sysPermEditBO) {
        SysPerm sysPerm = BeanUtil.copyProperties(sysPermEditBO, SysPerm.class);
        super.updateById(sysPerm);
        return BeanUtil.copyProperties(sysPerm, SysPermDTO.class);
    }

    @Override
    public SysPermDTO queryById(IdParam idParam) {
        SysPerm sysPerm = getById(idParam.getId());
        if (Objects.isNull(sysPerm)) {
            throw BizException.build("perm does not exist.");
        }
        return BeanUtil.copyProperties(sysPerm, SysPermDTO.class);
    }

    @Override
    public Boolean deleteById(IdParam idParam) {
        long count = this.lambdaQuery().eq(SysPerm::getPid, idParam.getId()).count();
        if (count > 0) {
            throw BizException.build("this perm have many children , cannot delete");
        }
        return removeById(idParam.getId());
    }

    @Override
    public Pageable<SysPermDTO> page(SysPermQueryPageBO sysPermQueryPageBO) {
        Page<SysPerm> page = this.lambdaQuery()
                .eq(Objects.nonNull(sysPermQueryPageBO.getPid()), SysPerm::getPid, sysPermQueryPageBO.getPid())
                .like(StringUtils.isNotBlank(sysPermQueryPageBO.getTitle()), SysPerm::getTitle, sysPermQueryPageBO.getTitle())
                .orderByDesc(SysPerm::getSortNum)
                .page(new Page<>(sysPermQueryPageBO.getPageNum(), sysPermQueryPageBO.getPageSize()));
        return PageUtil.toPage(page, SysPermDTO.class);
    }


    /**
     * 构造权限树
     *
     * @param roleId  角色id
     * @param isSuper 是否是超级管理员
     * @return perm tree
     */
    @Override
    public List<SysPermDTO> tree(Long roleId, boolean isSuper) {
        Map<Long, SysRolePerm> map = sysRolePermService.getRolePermMap(roleId);
        if (isSuper) {
            return queryRolePerms(-1L, map, true);
        } else {
            if (map.size() > 0) {
                List<SysPerm> topPerms = this.lambdaQuery().eq(SysPerm::getPid, -1L).in(SysPerm::getId, map.keySet()).orderByDesc(SysPerm::getSortNum).list();
                if (CollectionUtil.isNotEmpty(topPerms)) {
                    List<SysPermDTO> sysPermDTOS = new ArrayList<>();
                    for (SysPerm topPerm : topPerms) {
                        SysPermDTO target = BeanUtil.copyProperties(topPerm, SysPermDTO.class);
                        if (Objects.nonNull(map.get(target.getId()))) {
                            target.setChecked(true);
                        }
                        target.setChildren(queryRolePerms(target.getId(), map, false));
                        sysPermDTOS.add(target);
                    }
                    return sysPermDTOS;
                }
            }
            return Collections.emptyList();
        }
    }

    private List<SysPermDTO> queryRolePerms(Long pid, Map<Long, SysRolePerm> rolePermMap, boolean isSuper) {
        List<SysPerm> sysPerms = this.lambdaQuery().eq(SysPerm::getPid, pid).list();
        if (CollectionUtil.isNotEmpty(sysPerms)) {
            return sysPerms.stream().map(sysPerm -> {
                        boolean checked = Objects.nonNull(rolePermMap.get(sysPerm.getId()));
                        if (checked || isSuper) {
                            SysPermDTO sysPermDTO = BeanUtil.copyProperties(sysPerm, SysPermDTO.class);
                            sysPermDTO.setChecked(checked);
                            sysPermDTO.setChildren(queryRolePerms(sysPerm.getId(), rolePermMap, isSuper));
                            return sysPermDTO;
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .sorted(Collections.reverseOrder(Comparator.comparing(SysPermDTO::getSortNum)))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
