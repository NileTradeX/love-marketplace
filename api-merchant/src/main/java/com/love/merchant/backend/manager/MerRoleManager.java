package com.love.merchant.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.PageParam;
import com.love.common.util.PageableUtil;
import com.love.merchant.backend.model.param.MerRoleEditParam;
import com.love.merchant.backend.model.param.MerRoleSaveParam;
import com.love.merchant.backend.model.param.SettingPermsParam;
import com.love.merchant.backend.model.vo.MerPermVO;
import com.love.merchant.backend.model.vo.MerRoleVO;
import com.love.merchant.bo.MerRoleEditBO;
import com.love.merchant.bo.MerRoleQueryPageBO;
import com.love.merchant.bo.MerRoleSaveBO;
import com.love.merchant.bo.SettingPermsBO;
import com.love.merchant.client.MerchantRoleFeignClient;
import com.love.merchant.dto.MerPermDTO;
import com.love.merchant.dto.MerRoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerRoleManager {

    private final MerchantRoleFeignClient sysRoleFeignClient;

    public MerRoleVO save(MerRoleSaveParam sysRoleSaveParam) {
        MerRoleSaveBO sysRoleSaveBO = BeanUtil.copyProperties(sysRoleSaveParam, MerRoleSaveBO.class);
        MerRoleDTO sysRoleDTO = sysRoleFeignClient.save(sysRoleSaveBO);
        return BeanUtil.copyProperties(sysRoleDTO, MerRoleVO.class);
    }

    public MerRoleVO edit(MerRoleEditParam sysRoleEditParam) {
        MerRoleEditBO sysRoleEditBO = BeanUtil.copyProperties(sysRoleEditParam, MerRoleEditBO.class);
        MerRoleDTO sysRoleDTO = sysRoleFeignClient.edit(sysRoleEditBO);
        return BeanUtil.copyProperties(sysRoleDTO, MerRoleVO.class);
    }

    public MerRoleVO detail(IdParam idParam) {
        MerRoleDTO sysRoleDTO = sysRoleFeignClient.queryById(idParam);
        return BeanUtil.copyProperties(sysRoleDTO, MerRoleVO.class);
    }

    public Boolean deleteById(IdParam idParam) {
        return sysRoleFeignClient.deleteById(idParam);
    }

    public Pageable<MerRoleVO> page(PageParam pageParam) {
        MerRoleQueryPageBO sysRoleQueryPageBO = BeanUtil.copyProperties(pageParam, MerRoleQueryPageBO.class);
        Pageable<MerRoleDTO> sysRoleDTOS = sysRoleFeignClient.page(sysRoleQueryPageBO);
        return PageableUtil.toPage(sysRoleDTOS, MerRoleVO.class);
    }

    public List<MerPermVO> queryPerms(IdParam idParam) {
        List<MerPermDTO> list = sysRoleFeignClient.queryPerms(idParam.getId());
        if (Objects.isNull(list)) {
            return Collections.emptyList();
        }
        return BeanUtil.copyToList(list, MerPermVO.class);
    }

    public Boolean settingPerms(SettingPermsParam settingPermsParam) {
        SettingPermsBO sysRolePermSaveBO = new SettingPermsBO();
        sysRolePermSaveBO.setPermIds(settingPermsParam.getPermIds());
        sysRolePermSaveBO.setRoleId(settingPermsParam.getRoleId());
        return sysRoleFeignClient.settingPerms(sysRolePermSaveBO);
    }
}
