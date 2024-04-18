package com.love.merchant.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.merchant.bo.MerUserAdminBusinessInfoEditBO;
import com.love.merchant.bo.MerUserAdminBusinessInfoChangeEmailBO;
import com.love.merchant.bo.MerUserAdminBusinessInfoQueryByBizName;
import com.love.merchant.dto.MerUserAdminBusinessInfoDTO;
import com.love.merchant.entity.MerUserAdminBusinessInfo;
import com.love.merchant.entity.MerUserRole;
import com.love.merchant.mapper.MerUserAdminBusinessInfoMapper;
import com.love.merchant.service.MerUserAdminBusinessInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerUserAdminBusinessInfoServiceImpl extends ServiceImpl<MerUserAdminBusinessInfoMapper, MerUserAdminBusinessInfo> implements MerUserAdminBusinessInfoService{
    @Override
    public boolean save(MerUserAdminBusinessInfoEditBO businessInfoBO) {
        MerUserAdminBusinessInfo merUserAdminBusinessInfo = BeanUtil.copyProperties(businessInfoBO, MerUserAdminBusinessInfo.class);
        return this.save(merUserAdminBusinessInfo);
    }

    @Override
    public MerUserAdminBusinessInfoDTO queryById(Long id) {
        MerUserAdminBusinessInfo merUserAdminBusinessInfo = this.getById(id);

        return BeanUtil.copyProperties(merUserAdminBusinessInfo, MerUserAdminBusinessInfoDTO.class);
    }

    @Override
    public boolean updateById(MerUserAdminBusinessInfoEditBO businessInfo) {
        return this.updateById(BeanUtil.copyProperties(businessInfo, MerUserAdminBusinessInfo.class));
    }

    @Override
    public boolean changeBizOrderMgmtEmail(MerUserAdminBusinessInfoChangeEmailBO changeEmailBO) {
        return this.lambdaUpdate().set(MerUserAdminBusinessInfo::getBizOrderMgmtEmail, changeEmailBO.getBizOrderMgmtEmail())
                .eq(MerUserAdminBusinessInfo::getAdminId, changeEmailBO.getId()).update();
    }

    @Override
    public List<MerUserAdminBusinessInfoDTO> all() {
        return this.list().stream().map(merUserAdminBusinessInfo ->
                BeanUtil.copyProperties(merUserAdminBusinessInfo, MerUserAdminBusinessInfoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MerUserAdminBusinessInfoDTO queryByBizName(MerUserAdminBusinessInfoQueryByBizName queryByBizName) {
        MerUserAdminBusinessInfo merUserAdminBusinessInfo = this.lambdaQuery().eq(MerUserAdminBusinessInfo::getBizName, queryByBizName.getBizName()).one();

        return BeanUtil.copyProperties(merUserAdminBusinessInfo, MerUserAdminBusinessInfoDTO.class);
    }

}
