package com.love.marketplace.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.api.common.util.JwtUtil;
import com.love.common.Constants;
import com.love.common.enums.ExpireTime;
import com.love.common.user.SimpleUser;
import com.love.common.util.RedisUtil;
import com.love.marketplace.model.vo.LoginUserVO;
import com.love.user.sdk.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserTokenManager {

    private final RedisUtil redisUtil;
    private final long expire = ExpireTime.ONE_MONTH.getTime();

    public LoginUserVO createLoginToken(UserDTO userDTO) {
        SimpleUser simpleUser = new SimpleUser(userDTO.getId(), userDTO.getFullName());
        redisUtil.set(Constants.UID + ":" + userDTO.getUid(), simpleUser, expire);

        LoginUserVO loginVO = BeanUtil.copyProperties(userDTO, LoginUserVO.class);

        Map<String, Object> map = new HashMap<>();
        map.put(Constants.UID, userDTO.getUid());
        map.put(Constants.USER_ID, userDTO.getId());
        loginVO.setToken(JwtUtil.createJwt(map, expire * 1000L));

        return loginVO;
    }
}
