package com.love.merchant.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageUtil;
import com.love.common.util.RandomUtil;
import com.love.merchant.bo.*;
import com.love.merchant.dto.MerUserAdminDTO;
import com.love.merchant.dto.MerUserAdminInvitationDTO;
import com.love.merchant.entity.MerGroup;
import com.love.merchant.entity.MerUser;
import com.love.merchant.entity.MerUserAdminInvitation;
import com.love.merchant.entity.MerchantRegInfo;
import com.love.merchant.enums.InvitationStatus;
import com.love.merchant.mapper.MerUserAdminInvitationMapper;
import com.love.merchant.service.MerGroupService;
import com.love.merchant.service.MerUserAdminInvitationService;
import com.love.merchant.service.MerUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerUserAdminInvitationServiceImpl extends ServiceImpl<MerUserAdminInvitationMapper, MerUserAdminInvitation> implements MerUserAdminInvitationService {

    @Autowired
    private MerUserService merUserService;
    private final MerGroupService merGroupService;

    private String validCode(String code) {
        boolean exist = this.lambdaQuery().eq(MerUserAdminInvitation::getCode, code).exists();
        if (!exist) {
            return code;
        } else {
            return validCode(RandomUtil.randomStr(6));
        }
    }


    @Override
    public MerUserAdminInvitationDTO save(MerUserAdminInvitationSaveBO merUserAdminInvitationSaveBO) {
        MerUserAdminInvitation tmp = this.lambdaQuery().eq(MerUserAdminInvitation::getBizName, merUserAdminInvitationSaveBO.getBizName()).one();
        if (Objects.nonNull(tmp)) {
            throw BizException.build("%s is already been used", merUserAdminInvitationSaveBO.getBizName());
        }
        MerUserAdminInvitation merUserAdminInvitation = BeanUtil.copyProperties(merUserAdminInvitationSaveBO, MerUserAdminInvitation.class);
        merUserAdminInvitation.setStatus(InvitationStatus.WAIT_SEND.getStatus());
        String code = RandomUtil.randomStr(6);
        merUserAdminInvitation.setCode(validCode(code));
        this.save(merUserAdminInvitation);
        return BeanUtil.copyProperties(merUserAdminInvitation, MerUserAdminInvitationDTO.class);
    }

    @Override
    public boolean edit(MerUserAdminInvitationEditBO merUserAdminInvitationEditBO) {
        if (Objects.isNull(merUserAdminInvitationEditBO.getStatus()) && Objects.isNull(merUserAdminInvitationEditBO.getEmail())) {
            throw BizException.build("email and status cannot be null at the same time");
        }

        return this.lambdaUpdate()
                .set(Objects.nonNull(merUserAdminInvitationEditBO.getEmail()), MerUserAdminInvitation::getEmail, merUserAdminInvitationEditBO.getEmail())
                .set(Objects.nonNull(merUserAdminInvitationEditBO.getStatus()), MerUserAdminInvitation::getStatus, merUserAdminInvitationEditBO.getStatus())
                .set(MerUserAdminInvitation::getUpdateTime, LocalDateTime.now())
                .eq(MerUserAdminInvitation::getId, merUserAdminInvitationEditBO.getId())
                .update();
    }

    @Override
    public MerUserAdminInvitationDTO queryById(IdParam idParam) {
        return BeanUtil.copyProperties(this.getById(idParam.getId()), MerUserAdminInvitationDTO.class);
    }


    @Override
    public Pageable<MerUserAdminInvitationDTO> page(MerUserAdminInvitationQueryPageBO merUserAdminInvitationQueryPageBO) {
        Page<MerUserAdminInvitation> page = this.lambdaQuery()
                .eq(Objects.nonNull(merUserAdminInvitationQueryPageBO.getStatus()), MerUserAdminInvitation::getStatus, merUserAdminInvitationQueryPageBO.getStatus())
                .like(StringUtils.isNotBlank(merUserAdminInvitationQueryPageBO.getBizName()), MerUserAdminInvitation::getBizName, merUserAdminInvitationQueryPageBO.getBizName())
                .like(StringUtils.isNotBlank(merUserAdminInvitationQueryPageBO.getEmail()), MerUserAdminInvitation::getEmail, merUserAdminInvitationQueryPageBO.getEmail())
                .orderByDesc(MerUserAdminInvitation::getCreateTime)
                .page(new Page<>(merUserAdminInvitationQueryPageBO.getPageNum(), merUserAdminInvitationQueryPageBO.getPageSize()));
        return PageUtil.toPage(page, MerUserAdminInvitationDTO.class, (src, dst) -> {
            MerGroup merGroup = merGroupService.queryByName(src.getBizName());
            if (Objects.nonNull(merGroup)) {
                MerUser admin = merUserService.queryAdminByGroupId(merGroup.getId());
                if (Objects.nonNull(admin)) {
                    dst.setStatus(admin.getStatus());
                }
            }
        });
    }

    @Override
    public MerUserAdminInvitationDTO queryByCode(MerUserAdminInvitationQueryByCodeBO merUserAdminInvitationQueryByCodeBO) {
        MerUserAdminInvitation merUserAdminInvitation = this.lambdaQuery().eq(MerUserAdminInvitation::getCode, merUserAdminInvitationQueryByCodeBO.getCode()).one();
        if (Objects.isNull(merUserAdminInvitation)) {
            throw BizException.build("invitation is not exist");
        }

        MerUserAdminInvitationDTO merUserAdminInvitationDTO = BeanUtil.copyProperties(merUserAdminInvitation, MerUserAdminInvitationDTO.class);
        MerchantRegInfo merchantRegInfo = merUserService.queryMerchantRegInfo(merUserAdminInvitation.getBizName());
        if (Objects.nonNull(merchantRegInfo)) {
            MerUserAdminDTO merUserAdmin = merUserService.queryAdminById(MerUserAdminQueryBO.builder().userId(merchantRegInfo.getMerchantId()).build());
            if (Objects.isNull(merUserAdmin)) {
                throw BizException.build("query admin info fail");
            }
            merUserAdminInvitationDTO.setStatus(merUserAdmin.getStatus());
            merUserAdminInvitationDTO.setAdminInfo(merUserAdmin);
        }
        return merUserAdminInvitationDTO;
    }

    @Override
    public boolean updateByBizName(MerUserAdminInvitation userAdminInvitation) {
        return this.lambdaUpdate().eq(MerUserAdminInvitation::getBizName, userAdminInvitation.getBizName()).set(MerUserAdminInvitation::getStatus, userAdminInvitation.getStatus()).update();
    }

    @Override
    public MerUserAdminInvitation queryByGroupById(Long groupId) {
        return this.baseMapper.queryByGroupId(groupId);
    }
}
