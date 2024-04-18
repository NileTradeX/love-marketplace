package com.love.marketplace.manager;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.love.common.ErrorCode;
import com.love.common.bo.PasswordResetEmailSendBO;
import com.love.common.bo.WelcomeEmailSendBO;
import com.love.common.client.EmailSendFeignClient;
import com.love.common.exception.BizException;
import com.love.common.param.IdParam;
import com.love.common.util.EncryptUtil;
import com.love.common.util.GsonUtil;
import com.love.common.util.HttpUtil;
import com.love.marketplace.config.BoltConfigProperties;
import com.love.marketplace.model.dto.BoltAuthDTO;
import com.love.marketplace.model.dto.BoltUserDTO;
import com.love.marketplace.model.param.*;
import com.love.marketplace.model.vo.LoginUserVO;
import com.love.marketplace.model.vo.UserVO;
import com.love.order.bo.MigrateGuestOrderBO;
import com.love.order.client.OrderFeignClient;
import com.love.user.client.GuestFeignClient;
import com.love.user.client.UserAddressFeignClient;
import com.love.user.client.UserFeignClient;
import com.love.user.sdk.bo.*;
import com.love.user.sdk.dto.GuestDTO;
import com.love.user.sdk.dto.UserDTO;
import com.love.user.sdk.enums.UserSource;
import com.love.user.sdk.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class UserManager {

    private final UserFeignClient userFeignClient;

    private final EmailSendFeignClient emailSendFeignClient;

    private final UserAddressFeignClient userAddressFeignClient;

    private final UserTokenManager userTokenManager;

    private final OrderFeignClient orderFeignClient;

    private final GuestFeignClient guestFeignClient;

    private final BoltConfigProperties boltConfigProperties;

    private final TaskExecutor taskExecutor;

    @Value("${app.customer.active-url}")
    private String customerActiveUrl;

    @Value("${app.customer.reset-password-url}")
    private String customerResetPasswordUrl;

    public LoginUserVO register(UserRegisterParam userRegisterParam) {
        UserSaveBO userSaveBO = BeanUtil.copyProperties(userRegisterParam, UserSaveBO.class);
        userSaveBO.setLastLoginTime(LocalDateTime.now());
        UserDTO userDTO = userFeignClient.save(userSaveBO);
        this.sendWelcomeEmail(userDTO);
        return BeanUtil.copyProperties(userDTO, LoginUserVO.class);
    }

    public UserVO edit(UserEditParam userEditParam) {
        UserEditBO userEditBO = BeanUtil.copyProperties(userEditParam, UserEditBO.class);
        UserDTO userDTO = userFeignClient.edit(userEditBO);
        return BeanUtil.copyProperties(userDTO, UserVO.class);
    }

    public UserVO detail(UserIdParam userIdParam) {
        UserDTO userDTO = userFeignClient.detail(IdParam.builder().id(userIdParam.getUserId()).build());
        return BeanUtil.copyProperties(userDTO, UserVO.class);
    }

    public LoginUserVO login(UserLoginParam userLoginParam) {
        UserLoginBO userLoginBO = BeanUtil.copyProperties(userLoginParam, UserLoginBO.class);
        UserDTO userDTO = userFeignClient.login(userLoginBO);
        return userTokenManager.createLoginToken(userDTO);
    }

    public LoginUserVO loginWithBolt(UserLoginWithBoltParam userLoginWithBoltParam) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("scope", userLoginWithBoltParam.getScope());
        requestBody.put("grant_type", "authorization_code");
        requestBody.put("code", userLoginWithBoltParam.getCode());
        requestBody.put("client_id", boltConfigProperties.getAccount().getPublishableKey());
        requestBody.put("client_secret", boltConfigProperties.getAccount().getApiKey());
        requestBody.put("t", System.currentTimeMillis());
        String tokenRes;
        try {
            tokenRes = HttpUtil.post(boltConfigProperties.getApiUrl() + "/v1/oauth/token", requestBody);
        } catch (Exception e) {
            throw BizException.build("Invalid Bolt Code");
        }
        String accessToken = GsonUtil.json2bean(tokenRes, BoltAuthDTO.class).getAccessToken();

        Map<String, String> headers = new HashMap<>(2);
        headers.put("X-Api-Key", boltConfigProperties.getAccount().getApiKey());
        headers.put("Authorization", "bearer " + accessToken);
        String accountRes;
        try {
            accountRes = HttpUtil.get(boltConfigProperties.getApiUrl() + "/v1/account", headers);
        } catch (Exception e) {
            throw BizException.build("Invalid Bolt Code");
        }

        BoltUserDTO boltUserDTO = GsonUtil.json2bean(accountRes, BoltUserDTO.class);
        BoltUserDTO.Profile profile = boltUserDTO.getProfile();
        UserDTO userDTO = userFeignClient.queryByEmail(UserQueryByEmailBO.builder().email(profile.getEmail()).silent(true).build());
        if (Objects.isNull(userDTO)) {
            UserSaveBO userSaveBO = new UserSaveBO();
            userSaveBO.setFirstName(profile.getFirstName());
            userSaveBO.setLastName(profile.getLastName());
            userSaveBO.setPassword(UUID.randomUUID().toString());
            userSaveBO.setEmail(profile.getEmail());
            userSaveBO.setSource(UserSource.BOLT.getSource());
            userSaveBO.setStatus(UserStatus.ACTIVATED.getStatus());
            userDTO = userFeignClient.save(userSaveBO);
            //when registering
            asyncMigrateGuestOrder(userDTO);
        }

        List<BoltUserDTO.Addresses> addresses = boltUserDTO.getAddresses();
        if (!isEmpty(addresses) && isEmpty(userDTO.getAddressList())) {
            List<UserAddressSaveBO> addressSaveBOS = new ArrayList<>(addresses.size());
            for (BoltUserDTO.Addresses address : addresses) {
                UserAddressSaveBO addressSaveBO = new UserAddressSaveBO();
                addressSaveBO.setUserId(userDTO.getId());
                addressSaveBO.setFirstName(address.getFirstName());
                addressSaveBO.setLastName(address.getLastName());
                String phoneNumber = address.getPhoneNumber();
                String prefix = "+1 ";
                if (null != phoneNumber && phoneNumber.startsWith(prefix)) {
                    phoneNumber = phoneNumber.substring(prefix.length());
                }
                addressSaveBO.setPhoneNumber(StringUtils.deleteWhitespace(phoneNumber));
                addressSaveBO.setCity(address.getLocality());
                addressSaveBO.setState(address.getRegion());
                addressSaveBO.setZipCode(address.getPostalCode());
                addressSaveBO.setCompany(address.getCompany());
                addressSaveBO.setAddress(address.getStreetAddress1());
                addressSaveBO.setIsDefault(Boolean.TRUE.equals(address.getDefaultX()) ? 1 : 0);
                addressSaveBOS.add(addressSaveBO);
            }
            UserAddressBatchSaveBO addressBatchSaveBO = new UserAddressBatchSaveBO();
            addressBatchSaveBO.setAddressList(addressSaveBOS);
            userAddressFeignClient.saveBatch(addressBatchSaveBO);
        }

        return userTokenManager.createLoginToken(userDTO);
    }

    public Boolean changePassword(UserChangePasswordParam userResetPasswordParam) {
        UserChangePasswordBO userChangePasswordBO = BeanUtil.copyProperties(userResetPasswordParam, UserChangePasswordBO.class);
        userChangePasswordBO.setUserId(userResetPasswordParam.getUserId());
        return userFeignClient.changePassword(userChangePasswordBO);
    }

    public Boolean sendResetPasswordEmail(UserQueryByEmailParam userQueryByEmailParam) {
        UserQueryByEmailBO userQueryByEmailBO = BeanUtil.copyProperties(userQueryByEmailParam, UserQueryByEmailBO.class);
        UserDTO userDTO = userFeignClient.queryByEmail(userQueryByEmailBO);
        this.sendResetPasswordEmail(userDTO);
        return true;
    }

    private Long validateToken(String encStr) {
        String decryptStr = EncryptUtil.aesDecrypt(encStr);
        JSONObject data = JSON.parseObject(decryptStr);
        Long expirationMillis = data.getLong("expireTimestamp");
        if (Objects.isNull(expirationMillis) || System.currentTimeMillis() > expirationMillis) {
            throw BizException.buildWithCode(ErrorCode.M_TOKEN_EXPIRED_ERROR, "Token has expired");
        }

        Long userId = data.getLong("id");
        if (Objects.isNull(userId)) {
            throw BizException.buildWithCode(ErrorCode.M_USER_ID_CHECK_ERROR, "No user id in link!");
        }
        return userId;
    }

    public Boolean doResetPassword(UserResetPasswordParam userResetPasswordParam) {
        long userId = validateToken(userResetPasswordParam.getToken());
        UserResetPasswordBO userResetPasswordBO = new UserResetPasswordBO();
        userResetPasswordBO.setId(userId);
        userResetPasswordBO.setPassword(userResetPasswordParam.getPassword());
        return userFeignClient.resetPassword(userResetPasswordBO);
    }

    public LoginUserVO verifyEmail(TokenParam tokenParam) {
        long userId = validateToken(tokenParam.getToken());

        IdParam idParam = IdParam.builder().id(userId).build();
        boolean result = userFeignClient.verifyEmail(idParam);
        if (result) {
            UserDTO userDTO = userFeignClient.simple(idParam);
            asyncMigrateGuestOrder(userDTO);
            return userTokenManager.createLoginToken(userDTO);
        }
        throw BizException.build("Email active failed!");
    }

    private void asyncMigrateGuestOrder(UserDTO userDTO) {
        log.info("asyncMigrateGuestOrder,userDTO={}", userDTO);
        taskExecutor.execute(() -> {
                    try {
                        migrateGuestOrder(userDTO);
                    } catch (Throwable throwable) {
                        log.error("Migration guest order error, userDTO:{},error:{}", userDTO, throwable);
                    }
                }
        );
    }
    /**
     * @param userDTO
     * After activating the email, migrate the generated orders that were not logged in before
     */
    private void migrateGuestOrder(UserDTO userDTO) {
        GuestDTO guestDTO = guestFeignClient.queryByEmail(GuestBO.builder().email(userDTO.getEmail()).build());
        //If the guestDTO is not null, it indicates the order that needs to be migrated
        if(Objects.nonNull(guestDTO)){
            log.info("Start asynchronous migration of these orders ,user:{}",userDTO);
            orderFeignClient.migrateGuestOrder(
                    MigrateGuestOrderBO.builder()
                            .guestId(guestDTO.getId())
                            .userId(userDTO.getId())
                            .buyerName(userDTO.getFullName())
                            .build()
            );
        }
    }

    public Boolean resendActiveEmail(TokenParam tokenParam) {
        String decryptStr = EncryptUtil.aesDecrypt(tokenParam.getToken());
        JSONObject data = JSON.parseObject(decryptStr);
        Long userId = data.getLong("id");
        if (Objects.isNull(userId)) {
            throw BizException.build(ErrorCode.M_USER_ID_CHECK_ERROR, "No user id in link!");
        }
        this.sendWelcomeEmail(userFeignClient.detail(IdParam.builder().id(userId).build()));
        return true;
    }

    private String createToken(Long userId) {
        long nowMillis = System.currentTimeMillis();
        long expirationMillis = nowMillis + 30 * 60 * 1000;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", userId);
        jsonObject.put("expireTimestamp", expirationMillis);
        return EncryptUtil.aesEncrypt(jsonObject.toJSONString());
    }

    @Async
    public void sendWelcomeEmail(UserDTO userDTO) {
        String token = createToken(userDTO.getId());
        String emailCheckUrl = customerActiveUrl + "?token=" + token;
        WelcomeEmailSendBO welcomeEmailSendBO = WelcomeEmailSendBO.builder()
                .toEmail(userDTO.getEmail())
                .emailCheckUrl(emailCheckUrl)
                .build();
        emailSendFeignClient.sendWelcomeEmail(welcomeEmailSendBO);
    }

    @Async
    public void sendResetPasswordEmail(UserDTO userDTO) {
        String token = createToken(userDTO.getId());
        String passwordResetUrl = customerResetPasswordUrl + "?token=" + token;
        PasswordResetEmailSendBO passwordResetEmailSendBO = PasswordResetEmailSendBO.builder()
                .toEmail(userDTO.getEmail())
                .passwordResetUrl(passwordResetUrl)
                .build();
        emailSendFeignClient.sendPasswordResetEmail(passwordResetEmailSendBO);
    }

    public Boolean changeAvatar(UserChangeAvatarParam userChangeAvatarParam) {
        UserChangeAvatarBO userChangeAvatarBO = BeanUtil.copyProperties(userChangeAvatarParam, UserChangeAvatarBO.class);
        return userFeignClient.changeAvatar(userChangeAvatarBO);
    }

    public Boolean exist(UserExistParam userExistParam) {
        UserQueryByEmailBO userQueryByEmailBO = UserQueryByEmailBO.builder().email(userExistParam.getEmail()).silent(true).build();
        UserDTO userDTO = userFeignClient.queryByEmail(userQueryByEmailBO);
        return Objects.nonNull(userDTO);
    }
}
