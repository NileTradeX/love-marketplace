package com.love.merchant.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageUtil;
import com.love.merchant.bo.MerPermEditBO;
import com.love.merchant.bo.MerPermQueryPageBO;
import com.love.merchant.bo.MerPermSaveBO;
import com.love.merchant.dto.MerPermDTO;
import com.love.merchant.entity.MerPerm;
import com.love.merchant.entity.MerRolePerm;
import com.love.merchant.mapper.MerPermMapper;
import com.love.merchant.service.MerPermService;
import com.love.merchant.service.MerRolePermService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerPermServiceImpl extends ServiceImpl<MerPermMapper, MerPerm> implements MerPermService {

    private final MerRolePermService merRolePermService;

    @Override
    public MerPermDTO save(MerPermSaveBO merPermSaveBO) {
        MerPerm temp = this.lambdaQuery().eq(MerPerm::getTitle, merPermSaveBO.getTitle()).eq(MerPerm::getPid, merPermSaveBO.getPid()).one();
        if (Objects.nonNull(temp)) {
            throw BizException.build("perm already exists.");
        }

        MerPerm merPerm = BeanUtil.copyProperties(merPermSaveBO, MerPerm.class);
        super.save(merPerm);

        return BeanUtil.copyProperties(merPerm, MerPermDTO.class);
    }

    @Override
    public MerPermDTO edit(MerPermEditBO merPermEditBO) {
        MerPerm merPerm = BeanUtil.copyProperties(merPermEditBO, MerPerm.class);
        super.updateById(merPerm);
        return BeanUtil.copyProperties(merPerm, MerPermDTO.class);
    }

    @Override
    public MerPermDTO queryById(IdParam idParam) {
        MerPerm merPerm = getById(idParam.getId());
        if (Objects.isNull(merPerm)) {
            throw BizException.build("perm does not exist.");
        }
        return BeanUtil.copyProperties(merPerm, MerPermDTO.class);
    }

    @Override
    public Boolean deleteById(IdParam idParam) {
        long count = this.lambdaQuery().eq(MerPerm::getPid, idParam.getId()).count();
        if (count > 0) {
            throw BizException.build("this perm have many children , cannot delete");
        }
        return removeById(idParam.getId());
    }

    @Override
    public Pageable<MerPermDTO> page(MerPermQueryPageBO merPermQueryPageBO) {
        Page<MerPerm> merPermPage = this.lambdaQuery()
                .eq(Objects.nonNull(merPermQueryPageBO.getPid()), MerPerm::getPid, merPermQueryPageBO.getPid())
                .like(StringUtils.isNotBlank(merPermQueryPageBO.getTitle()), MerPerm::getTitle, merPermQueryPageBO.getTitle())
                .orderByDesc(MerPerm::getSortNum)
                .page(new Page<>(merPermQueryPageBO.getPageNum(), merPermQueryPageBO.getPageSize()));
        return PageUtil.toPage(merPermPage, MerPermDTO.class);
    }


    /**
     * 构造权限树
     *
     * @param roleId  角色id
     * @param isSuper 是否是超级管理员
     * @return perm tree
     */
    @Override
    public List<MerPermDTO> tree(Long roleId, boolean isSuper) {
        Map<Long, MerRolePerm> map = merRolePermService.getRolePermMap(roleId);
        if (isSuper) {
            return queryRolePerms(-1L, map, true);
        } else {
            if (map.size() > 0) {
                List<MerPerm> topPerms = this.lambdaQuery().eq(MerPerm::getPid, -1L).in(MerPerm::getId, map.keySet()).orderByDesc(MerPerm::getSortNum).list();
                if (CollectionUtil.isNotEmpty(topPerms)) {
                    List<MerPermDTO> merPermDTOS = new ArrayList<>();
                    for (MerPerm topPerm : topPerms) {
                        MerPermDTO target = BeanUtil.copyProperties(topPerm, MerPermDTO.class);
                        if (Objects.nonNull(map.get(target.getId()))) {
                            target.setChecked(true);
                        }
                        target.setChildren(queryRolePerms(target.getId(), map, false));
                        merPermDTOS.add(target);
                    }
                    return merPermDTOS;
                }
            }
            return Collections.emptyList();
        }
    }

    private List<MerPermDTO> queryRolePerms(Long pid, Map<Long, MerRolePerm> rolePermMap, boolean isSuper) {
        List<MerPerm> merPerms = this.lambdaQuery().eq(MerPerm::getPid, pid).list();
        if (CollectionUtil.isNotEmpty(merPerms)) {
            return merPerms.stream().map(sysPerm -> {
                        boolean checked = Objects.nonNull(rolePermMap.get(sysPerm.getId()));
                        if (checked || isSuper) {
                            MerPermDTO merPermDTO = BeanUtil.copyProperties(sysPerm, MerPermDTO.class);
                            merPermDTO.setChecked(checked);
                            merPermDTO.setChildren(queryRolePerms(sysPerm.getId(), rolePermMap, isSuper));
                            return merPermDTO;
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .sorted(Collections.reverseOrder(Comparator.comparing(MerPermDTO::getSortNum)))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
