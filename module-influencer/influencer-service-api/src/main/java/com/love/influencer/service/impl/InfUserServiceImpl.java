package com.love.influencer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.ObjectUtil;
import com.love.common.util.PageUtil;
import com.love.common.util.RandomUtil;
import com.love.influencer.bo.*;
import com.love.influencer.dto.InfUserAddressDTO;
import com.love.influencer.dto.InfUserDTO;
import com.love.influencer.dto.InfUserInfoDTO;
import com.love.influencer.entity.InfUser;
import com.love.influencer.enums.InfUserStatus;
import com.love.influencer.mapper.InfUserMapper;
import com.love.influencer.service.InfStoreService;
import com.love.influencer.service.InfUserAddressService;
import com.love.influencer.service.InfUserService;
import com.love.influencer.util.BCryptUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfUserServiceImpl extends ServiceImpl<InfUserMapper, InfUser> implements InfUserService {

    @Autowired
    private InfStoreService infStoreService;
    private final InfUserAddressService infUserAddressService;


    private InfUser checkExists(String account) {
        return this.lambdaQuery().apply("lower(account) = '" + account.toLowerCase() + "'").one();
    }

    private String validCode(String code) {
        boolean exist = this.lambdaQuery().eq(InfUser::getCode, code).exists();
        if (!exist) {
            return code;
        } else {
            return validCode(RandomUtil.randomStr(6));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InfUserDTO save(InfUserSaveBO infUserSaveBO) {
        InfUser temp = checkExists(infUserSaveBO.getAccount());
        if (Objects.nonNull(temp)) {
            throw BizException.build("Account already exists");
        }

        infUserSaveBO.setPassword(ObjectUtil.ifNull(infUserSaveBO.getPassword(), "12345678"));
        InfUser influencerUser = BeanUtil.copyProperties(infUserSaveBO, InfUser.class);
        influencerUser.setPassword(BCryptUtils.encode(influencerUser.getPassword()));
        influencerUser.setCode(validCode(RandomUtil.randomStr(6)));
        influencerUser.setStatus(0);
        this.save(influencerUser);
        return BeanUtil.copyProperties(influencerUser, InfUserDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(InfUserEditBO infUserEditBO) {
        InfUser user = this.getById(infUserEditBO.getId());
        if (!Objects.equals(infUserEditBO.getCode(), user.getCode())) {
            throw BizException.build("code is wrong");
        }

        boolean register = false;
        if (infUserEditBO.getCode() != null && user.getStatus() == InfUserStatus.PENDING.getStatus()) {
            infUserEditBO.setCode(null);
            register = true;
        }

        InfUser influencerUser = BeanUtil.copyProperties(infUserEditBO, InfUser.class);
        if (Objects.nonNull(infUserEditBO.getPassword())) {
            influencerUser.setPassword(BCryptUtils.encode(influencerUser.getPassword()));
        }

        if (register) {
            influencerUser.setStatus(InfUserStatus.ACTIVE.getStatus());
        }

        boolean update = this.updateById(influencerUser);

        if (Objects.nonNull(infUserEditBO.getUserAddress())) {
            infUserEditBO.getUserAddress().setInfluencerId(influencerUser.getId());
            if (Objects.isNull(infUserEditBO.getUserAddress().getId())) {
                infUserAddressService.save(infUserEditBO.getUserAddress());
            } else {
                infUserAddressService.edit(infUserEditBO.getUserAddress());
            }
        }
        return update;
    }

    @Override
    public InfUserInfoDTO queryById(IdParam idParam) {
        InfUser infUser = this.getById(idParam.getId());
        if (Objects.isNull(infUser)) {
            throw BizException.build("user does not exist.");
        }
        InfUserInfoDTO infUserInfoDTO = BeanUtil.copyProperties(infUser, InfUserInfoDTO.class);
        List<InfUserAddressDTO> infUserAddressDTOS = infUserAddressService.queryByUserId(InfStoreQueryByInfIdBO.builder().influencerId(idParam.getId()).build());
        if (CollectionUtil.isNotEmpty(infUserAddressDTOS)) {
            infUserInfoDTO.setUserAddress(infUserAddressDTOS.get(0));
        }
        return infUserInfoDTO;
    }


    @Override
    public Boolean deleteById(IdParam idParam) {
        return this.removeById(idParam.getId())
                && infUserAddressService.deleteByInfluencerId(idParam.getId())
                && infStoreService.deleteByInfluencerId(idParam.getId());
    }

    @Override
    public Pageable<InfUserDTO> page(InfUserQueryPageBO infUserQueryPageBO) {
        Page<InfUser> page = this.lambdaQuery()
                .like(StringUtils.isNotBlank(infUserQueryPageBO.getAccount()), InfUser::getAccount, infUserQueryPageBO.getAccount())
                .like(StringUtils.isNotBlank(infUserQueryPageBO.getUsername()), InfUser::getUsername, infUserQueryPageBO.getUsername())
                .orderByDesc(InfUser::getCreateTime)
                .page(new Page<>(infUserQueryPageBO.getPageNum(), infUserQueryPageBO.getPageSize()));
        return PageUtil.toPage(page, InfUserDTO.class);
    }

    @Override
    public Boolean changePassword(InfUserChangePasswordBO infUserChangePasswordBO) {
        String encodePassword = BCryptUtils.encode(infUserChangePasswordBO.getPassword());
        return this.lambdaUpdate().set(InfUser::getPassword, encodePassword).eq(InfUser::getId, infUserChangePasswordBO.getId()).update();
    }

    @Override
    public Boolean resetPassword(InfUserResetPasswordBO infUserResetPasswordBO) {
        return this.lambdaUpdate().set(InfUser::getPassword, BCryptUtils.encode(infUserResetPasswordBO.getPassword())).eq(InfUser::getId, infUserResetPasswordBO.getId()).update();
    }

    @Override
    public InfUserDTO login(InfUserLoginBO infUserLoginBO) {
        InfUser tmp = this.lambdaQuery().apply("UPPER(account) = '" + infUserLoginBO.getAccount().toUpperCase() + "'").one();
        if (Objects.isNull(tmp) || Objects.isNull(tmp.getId())) {
            throw BizException.build("Account : %s does not exist.", infUserLoginBO.getAccount());
        }

        if (!BCryptUtils.matches(infUserLoginBO.getPassword(), tmp.getPassword())) {
            throw BizException.build("Authentication failed. Please check your username and password and try again.");
        }

        if (tmp.getStatus() != InfUserStatus.ACTIVE.getStatus()) {
            throw BizException.build("Your account is not active!");
        }

        tmp.setLastLoginTime(LocalDateTime.now());
        this.updateById(tmp);

        return BeanUtil.copyProperties(tmp, InfUserDTO.class);

    }

    @Override
    public Boolean changeAvatar(InfUserChangeAvatarBO infUserChangeAvatarBO) {
        return this.lambdaUpdate().set(InfUser::getAvatar, infUserChangeAvatarBO.getAvatar()).eq(InfUser::getId, infUserChangeAvatarBO.getId()).update();
    }

    @Override
    public InfUser queryInfluencerById(IdParam idParam) {
        return this.getById(idParam);
    }

    @Override
    public InfUserDTO queryByCode(InfUserQueryByCodeBO infUserQueryByCodeBO) {
        InfUser user = this.lambdaQuery().eq(InfUser::getCode, infUserQueryByCodeBO.getCode()).one();
        if (Objects.isNull(user)) {
            throw BizException.build("Influencer not found!");
        }
        user.setCode(infUserQueryByCodeBO.getCode());
        return BeanUtil.copyProperties(user, InfUserDTO.class);
    }

    @Override
    public Boolean updateStatus(InfUserUpdateStatusBO infUserUpdateStatusBO) {
        return this.lambdaUpdate().set(InfUser::getStatus, infUserUpdateStatusBO.getStatus()).eq(InfUser::getId, infUserUpdateStatusBO.getId()).update(new InfUser());
    }
}
