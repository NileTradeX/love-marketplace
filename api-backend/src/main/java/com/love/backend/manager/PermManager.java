package com.love.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.backend.model.param.*;
import com.love.backend.model.vo.PermVO;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageableUtil;
import com.love.merchant.bo.MerPermEditBO;
import com.love.merchant.bo.MerPermQueryPageBO;
import com.love.merchant.bo.MerPermSaveBO;
import com.love.merchant.client.MerchantPermFeignClient;
import com.love.merchant.dto.MerPermDTO;
import com.love.rbac.bo.SysPermEditBO;
import com.love.rbac.bo.SysPermQueryPageBO;
import com.love.rbac.bo.SysPermSaveBO;
import com.love.rbac.client.SysPermFeignClient;
import com.love.rbac.dto.SysPermDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PermManager {

    private final SysPermFeignClient sysPermFeignClient;
    private final MerchantPermFeignClient merPermFeignClient;

    public PermVO save(PermSaveParam permSaveParam) {
        int flag = permSaveParam.getFlag();
        if (flag == 1) {
            SysPermSaveBO sysPermSaveBO = BeanUtil.copyProperties(permSaveParam, SysPermSaveBO.class);
            SysPermDTO sysPermDTO = sysPermFeignClient.save(sysPermSaveBO);
            return BeanUtil.copyProperties(sysPermDTO, PermVO.class);
        } else if (flag == 2) {
            MerPermSaveBO merPermSaveBO = BeanUtil.copyProperties(permSaveParam, MerPermSaveBO.class);
            MerPermDTO merPermDTO = merPermFeignClient.save(merPermSaveBO);
            return BeanUtil.copyProperties(merPermDTO, PermVO.class);
        }
        return null;
    }

    public PermVO edit(PermEditParam permEditParam) {
        int flag = permEditParam.getFlag();
        if (flag == 1) {
            SysPermEditBO sysPermEditBO = BeanUtil.copyProperties(permEditParam, SysPermEditBO.class);
            SysPermDTO sysPermDTO = sysPermFeignClient.edit(sysPermEditBO);
            return BeanUtil.copyProperties(sysPermDTO, PermVO.class);
        } else if (flag == 2) {
            MerPermEditBO merPermEditBO = BeanUtil.copyProperties(permEditParam, MerPermEditBO.class);
            MerPermDTO merPermDTO = merPermFeignClient.edit(merPermEditBO);
            return BeanUtil.copyProperties(merPermDTO, PermVO.class);
        }
        return null;
    }

    public Boolean deleteById(PermDeleteParam permDeleteParam) {
        int flag = permDeleteParam.getFlag();
        if (flag == 1) {
            return sysPermFeignClient.deleteById(IdParam.builder().id(permDeleteParam.getId()).build());
        } else if (flag == 2) {
            return merPermFeignClient.deleteById(IdParam.builder().id(permDeleteParam.getId()).build());
        }
        return false;
    }

    public Pageable<PermVO> page(PermQueryPageParam permQueryPageParam) {
        int flag = permQueryPageParam.getFlag();
        if (flag == 1) {
            SysPermQueryPageBO sysPermQueryPageBO = BeanUtil.copyProperties(permQueryPageParam, SysPermQueryPageBO.class);
            Pageable<SysPermDTO> pageable = sysPermFeignClient.page(sysPermQueryPageBO);
            return PageableUtil.toPage(pageable, PermVO.class);
        } else if (flag == 2) {
            MerPermQueryPageBO merPermQueryPageBO = BeanUtil.copyProperties(permQueryPageParam, MerPermQueryPageBO.class);
            Pageable<MerPermDTO> pageable = merPermFeignClient.page(merPermQueryPageBO);
            return PageableUtil.toPage(pageable, PermVO.class);
        }
        return PageableUtil.emptyPage(1, 1);
    }

    public List<PermVO> tree(PermTreeQueryParam permTreeQueryParam) {
        int flag = permTreeQueryParam.getFlag();
        List<?> list = new ArrayList<>();

        IdParam idParam = IdParam.builder().id(permTreeQueryParam.getId()).build();

        if (1 == flag) {
            list = sysPermFeignClient.tree(idParam);
        } else if (2 == flag) {
            list = merPermFeignClient.tree(idParam);
        }

        if (Objects.nonNull(list)) {
            return BeanUtil.copyToList(list, PermVO.class);
        }

        return Collections.emptyList();
    }
}
