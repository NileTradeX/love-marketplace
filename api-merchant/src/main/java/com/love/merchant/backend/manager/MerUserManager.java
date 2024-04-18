package com.love.merchant.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.love.api.common.util.JwtUtil;
import com.love.common.Constants;
import com.love.common.bo.PasswordResetEmailSendBO;
import com.love.common.client.EmailSendFeignClient;
import com.love.common.enums.ExpireTime;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.user.SimpleUser;
import com.love.common.util.EncryptUtil;
import com.love.common.util.PageableUtil;
import com.love.common.util.RedisUtil;
import com.love.merchant.backend.model.param.*;
import com.love.merchant.backend.model.vo.LoginUserVO;
import com.love.merchant.backend.model.vo.MerUserVO;
import com.love.merchant.bo.*;
import com.love.merchant.client.MerchantUserFeignClient;
import com.love.merchant.dto.LoginUserDTO;
import com.love.merchant.dto.MerPermDTO;
import com.love.merchant.dto.MerUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerUserManager {
    private final RedisUtil redisUtil;
    private final MerchantUserFeignClient merchantUserFeignClient;
    private final EmailSendFeignClient emailSendFeignClient;

    @Value("${app.merchant.reset-password-url}")
    private String resetPasswordUrl;

    public MerUserVO save(MerUserSaveParam sysUserSaveParam) {
        MerUserSaveBO sysUserSaveBO = BeanUtil.copyProperties(sysUserSaveParam, MerUserSaveBO.class);
        MerUserDTO sysUserDTO = merchantUserFeignClient.save(sysUserSaveBO);
        return BeanUtil.copyProperties(sysUserDTO, MerUserVO.class);
    }

    public MerUserVO edit(MerUserEditParam sysUserEditParam) {
        MerUserEditBO sysUserEditBO = BeanUtil.copyProperties(sysUserEditParam, MerUserEditBO.class);
        MerUserDTO sysUserDTO = merchantUserFeignClient.edit(sysUserEditBO);
        return BeanUtil.copyProperties(sysUserDTO, MerUserVO.class);
    }

    public Pageable<MerUserVO> page(MerUserQueryPageParam userQueryPageParam) {
        MerUserQueryPageBO sysUserQueryPageBO = BeanUtil.copyProperties(userQueryPageParam, MerUserQueryPageBO.class);
        Pageable<MerUserDTO> pageable = merchantUserFeignClient.page(sysUserQueryPageBO);
        return PageableUtil.toPage(pageable, MerUserVO.class);
    }

    public Boolean changePassword(MerUserChangePasswordParam userChangePasswordParam) {
        MerUserChangePasswordBO merUserChangePasswordBO = BeanUtil.copyProperties(userChangePasswordParam, MerUserChangePasswordBO.class);
        return merchantUserFeignClient.changePassword(merUserChangePasswordBO);
    }

    public Boolean resetPassword(MerUserResetPasswordParam merUserResetPasswordParam) {
        MerUserResetPasswordBO merUserResetPasswordBO = BeanUtil.copyProperties(merUserResetPasswordParam, MerUserResetPasswordBO.class);
        return merchantUserFeignClient.resetPassword(merUserResetPasswordBO);
    }

    public Boolean deleteById(IdParam idParam) {
        return merchantUserFeignClient.deleteById(idParam);
    }

    public LoginUserVO login(MerUserLoginParam sysUserLoginParam) {
        MerUserLoginBO sysUserLoginBO = BeanUtil.copyProperties(sysUserLoginParam, MerUserLoginBO.class);
        LoginUserDTO loginUserDTO = merchantUserFeignClient.login(sysUserLoginBO);

        MerUserDTO sysUser = loginUserDTO.getUser();
        List<String> apisList = new ArrayList<>();
        this.getApisList(loginUserDTO.getPerms(), apisList);

        SimpleUser simpleUser = BeanUtil.copyProperties(sysUser, SimpleUser.class);
        if (Objects.isNull(simpleUser.getRoleId()) && !sysUser.isAdmin()) {
            throw BizException.build("user role is not assigned");
        }


        long expire = ExpireTime.ONE_DAY.getTime() / 2L;

        redisUtil.set(Constants.UID + ":" + sysUser.getUid(), simpleUser, expire);
        redisUtil.set(Constants.ROLE_PERMS + ":" + sysUser.getRoleId(), apisList, expire);

        Map<String, Object> map = new HashMap<>();
        map.put(Constants.UID, simpleUser.getUid());
        map.put(Constants.USER_ID, simpleUser.getId());
        String token = JwtUtil.createJwt(map, expire * 1000L);
        LoginUserVO loginUserVO = BeanUtil.copyProperties(loginUserDTO, LoginUserVO.class);
        loginUserVO.setToken(token);

        return loginUserVO;
    }

    private void getApisList(List<MerPermDTO> lst, List<String> apisList) {
        if (CollectionUtil.isEmpty(lst)) {
            return;
        }

        for (MerPermDTO node : lst) {
            String apis = node.getApis();
            if (apis != null && !apis.isEmpty()) {
                apisList.addAll(Arrays.asList(apis.split(",")));
            }

            if (node.getChildren() != null) {
                getApisList(node.getChildren(), apisList);
            } else return;
        }
    }

    public Boolean resetAdminPassword(MerUserQueryByAccountParam merUserResetPasswordParam) {
        MerUserQueryByAccountBO merUserQueryByAccountBO = BeanUtil.copyProperties(merUserResetPasswordParam, MerUserQueryByAccountBO.class);
        MerUserDTO merUserDTO = merchantUserFeignClient.queryByAccount(merUserQueryByAccountBO);
        this.resetAdminPassword(merUserDTO);
        return true;
    }

    private void resetAdminPassword(MerUserDTO userDTO) {
        long nowMillis = System.currentTimeMillis();
        long expirationMillis = nowMillis + 30 * 60 * 1000;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", userDTO.getId());
        jsonObject.put("expireTimestamp", expirationMillis);
        String token = EncryptUtil.aesEncrypt(jsonObject.toJSONString());
        String passwordResetUrl = resetPasswordUrl + "?token=" + token;
        PasswordResetEmailSendBO passwordResetEmailSendBO = PasswordResetEmailSendBO.builder()
                .toEmail(userDTO.getAccount())
                .passwordResetUrl(passwordResetUrl)
                .build();
        emailSendFeignClient.sendPasswordResetEmail(passwordResetEmailSendBO);
    }

    public Boolean doResetPassword(MerUserResetAdminPasswordParam merUserResetAdminPasswordParam) {
        String decryptStr = EncryptUtil.aesDecrypt(merUserResetAdminPasswordParam.getToken());
        JSONObject jsonObject = JSONObject.parseObject(decryptStr);
        Long expirationMillis = jsonObject.getLong("expireTimestamp");
        if (Objects.isNull(expirationMillis) || System.currentTimeMillis() > expirationMillis) {
            throw BizException.build("Token has expired");
        }

        Long userId = jsonObject.getLong("id");
        if (Objects.isNull(userId)) {
            throw BizException.build("No user id in link!");
        }

        MerUserResetPasswordBO merUserResetPasswordBO = new MerUserResetPasswordBO();
        merUserResetPasswordBO.setId(userId);
        merUserResetPasswordBO.setPassword(merUserResetAdminPasswordParam.getPassword());
        return merchantUserFeignClient.resetPassword(merUserResetPasswordBO);
    }
}
