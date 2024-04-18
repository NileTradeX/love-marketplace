package com.love.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.common.util.ObjectUtil;
import com.love.common.util.PageUtil;
import com.love.common.util.SortUtil;
import com.love.user.entity.User;
import com.love.user.mapper.UserMapper;
import com.love.user.sdk.bo.*;
import com.love.user.sdk.dto.UserDTO;
import com.love.user.sdk.enums.UserSource;
import com.love.user.sdk.enums.UserStatus;
import com.love.user.service.UserAddressService;
import com.love.user.service.UserService;
import com.love.user.util.BCryptUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserAddressService userAddressService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO save(UserSaveBO userSaveBO) {
        long userExisted = this.lambdaQuery().eq(User::getEmail, userSaveBO.getEmail()).count();
        if (userExisted > 0) {
            throw BizException.build("This email %s is already exist", userSaveBO.getEmail());
        }

        User user = BeanUtil.copyProperties(userSaveBO, User.class);
        user.setPassword(BCryptUtils.encode(user.getPassword()));
        user.setStatus(ObjectUtil.ifNull(user.getStatus(), UserStatus.DEACTIVATED.getStatus()));
        user.setUid("C" + IdWorker.getIdStr());
        user.setSource(ObjectUtil.ifNull(userSaveBO.getSource(), UserSource.LOVE.getSource()));
        boolean result = this.save(user);
        List<UserAddressSaveBO> userAddressSaveBOS = userSaveBO.getAddressList();
        if (result && CollUtil.isNotEmpty(userAddressSaveBOS)) {
            userAddressSaveBOS.forEach(t -> t.setUserId(user.getId()));
            userAddressService.saveBatch(userAddressSaveBOS);
        }
        return BeanUtil.copyProperties(user, UserDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO edit(UserEditBO userEditBO) {
        if (!StringUtils.isBlank(userEditBO.getEmail())) {
            boolean userExisted = this.lambdaQuery().eq(User::getEmail, userEditBO.getEmail()).ne(User::getId, userEditBO.getId()).exists();
            if (userExisted) {
                throw BizException.build("This email %s is already exist", userEditBO.getEmail());
            }
        }

        User user = BeanUtil.copyProperties(userEditBO, User.class);
        if (StringUtils.isNotBlank(userEditBO.getPassword())) {
            user.setPassword(BCryptUtils.encode(userEditBO.getPassword()));
        }

        boolean result = this.updateById(user);
        if (!result) {
            throw BizException.build("User update failed!");
        }

        List<UserAddressEditBO> addressEditBOList = userEditBO.getAddressList();
        if (CollUtil.isNotEmpty(addressEditBOList)) {
            List<UserAddressSaveBO> userAddressSaveBOS = BeanUtil.copyToList(addressEditBOList, UserAddressSaveBO.class);
            userAddressSaveBOS.forEach(t -> t.setUserId(user.getId()));
            userAddressService.saveBatch(userAddressSaveBOS);
        }
        return BeanUtil.copyProperties(user, UserDTO.class);
    }

    @Override
    public UserDTO queryById(IdParam idParam, boolean simple) {
        User user = this.getById(idParam.getId());
        if (Objects.isNull(user)) {
            throw BizException.build("User does not exist");
        }

        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        if (!simple) {
            userDTO.setAddressList(userAddressService.queryByUserId(UserAddressQueryListBO.builder().userId(idParam.getId()).build()));
        }
        return userDTO;
    }

    @Override
    public UserDTO queryByEmail(UserQueryByEmailBO userQueryByEmailBO) {
        User user = this.lambdaQuery().eq(User::getEmail, userQueryByEmailBO.getEmail()).one();
        if (Objects.isNull(user)) {
            if (userQueryByEmailBO.isSilent()) {
                return null;
            }
            throw BizException.build("User not found with email %s", userQueryByEmailBO.getEmail());
        }

        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        if (!userQueryByEmailBO.isSimple()) {
            userDTO.setAddressList(userAddressService.queryByUserId(UserAddressQueryListBO.builder().userId(user.getId()).build()));
        }
        return userDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(IdParam idParam) {
        return this.removeById(idParam.getId());
    }

    @Override
    public Pageable<UserDTO> page(UserQueryPageBO userQueryPageBO) {
        QueryChainWrapper<User> queryWrapper = this.query();
        if (Objects.nonNull(userQueryPageBO.getCustomerName())) {
            String customerName = userQueryPageBO.getCustomerName().trim().toLowerCase();
            String[] names = StringUtils.split(customerName);
            if (names.length < 2) {
                queryWrapper.like("lower(first_name)", customerName);
                queryWrapper.or();
                queryWrapper.like("lower(last_name)", customerName);
            } else {
                String firstName = names[0];
                String lastName = names[1];
                queryWrapper.like("lower(first_name)", firstName);
                queryWrapper.or();
                queryWrapper.like("lower(last_name)", lastName);
            }
        }

        if (Objects.nonNull(userQueryPageBO.getSort())) {
            SortUtil sortUtil = new SortUtil(userQueryPageBO.getSort());
            queryWrapper.orderBy(true, sortUtil.isAsc(), sortUtil.getSortField());
        } else {
            queryWrapper.orderByDesc("create_time");
        }

        Page<User> page = queryWrapper.page(new Page<>(userQueryPageBO.getPageNum(), userQueryPageBO.getPageSize()));
        return PageUtil.toPage(page, UserDTO.class);
    }


    @Override
    public UserDTO login(UserLoginBO userLoginBO) {
        User user = this.query().eq("lower(email)", userLoginBO.getEmail().trim().toLowerCase()).one();
        if (Objects.isNull(user)) {
            throw BizException.build("This email does not exist");
        }

        if (user.getStatus() == UserStatus.DEACTIVATED.getStatus()) {
            throw new BizException("User account is not active");
        }

        if (!BCryptUtils.matches(userLoginBO.getPassword(), user.getPassword())) {
            throw BizException.build("Password is wrong");
        }

        this.lambdaUpdate().set(User::getLastLoginTime, LocalDateTimeUtil.now()).eq(User::getId, user.getId()).update();

        return BeanUtil.copyProperties(user, UserDTO.class);
    }

    @Override
    public Boolean changePassword(UserChangePasswordBO userChangePasswordBO) {
        User tmp = this.lambdaQuery().eq(User::getId, userChangePasswordBO.getUserId()).one();
        boolean matches = BCryptUtils.matches(userChangePasswordBO.getOldPassword(), tmp.getPassword());
        if (!matches) {
            throw BizException.build("The original password is incorrect");
        }

        String encodePassword = BCryptUtils.encode(userChangePasswordBO.getNewPassword());
        return this.lambdaUpdate().set(User::getPassword, encodePassword).eq(User::getId, userChangePasswordBO.getUserId()).update();
    }

    @Override
    public Boolean resetPassword(UserResetPasswordBO userResetPasswordBO) {
        return this.lambdaUpdate().set(User::getPassword, BCryptUtils.encode(userResetPasswordBO.getPassword())).eq(User::getId, userResetPasswordBO.getId()).update();
    }

    @Override
    public Boolean verifyEmail(IdParam idParam) {
        User user = this.getById(idParam.getId());
        if (Objects.isNull(user)) {
            throw BizException.build("This email does not exist.");
        }

        return this.lambdaUpdate().set(User::getStatus, UserStatus.ACTIVATED.getStatus()).eq(User::getId, user.getId()).update();
    }

    @Override
    public Boolean changeAvatar(UserChangeAvatarBO userChangeAvatarBO) {
        return this.lambdaUpdate().set(User::getAvatar, userChangeAvatarBO.getAvatar()).eq(User::getId, userChangeAvatarBO.getUserId()).update();
    }

    @Override
    public List<UserDTO> queryByIdList(IdsParam idsParam) {
        return BeanUtil.copyToList(this.lambdaQuery().in(User::getId, idsParam.getIdList()).list(), UserDTO.class);
    }
}
