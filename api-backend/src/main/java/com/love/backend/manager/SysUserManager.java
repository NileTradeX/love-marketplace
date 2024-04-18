package com.love.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.love.api.common.util.JwtUtil;
import com.love.backend.model.param.*;
import com.love.backend.model.vo.LoginUserVO;
import com.love.backend.model.vo.SysUserVO;
import com.love.common.Constants;
import com.love.common.enums.ExpireTime;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.user.SimpleUser;
import com.love.common.util.PageableUtil;
import com.love.common.util.RedisUtil;
import com.love.rbac.bo.*;
import com.love.rbac.client.SysUserFeignClient;
import com.love.rbac.dto.LoginUserDTO;
import com.love.rbac.dto.SysPermDTO;
import com.love.rbac.dto.SysUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysUserManager {

    private final RedisUtil redisUtil;
    private final SysUserFeignClient sysUserFeignClient;

    public SysUserVO save(SysUserSaveParam sysUserSaveParam) {
        SysUserSaveBO sysUserSaveBO = BeanUtil.copyProperties(sysUserSaveParam, SysUserSaveBO.class);
        SysUserDTO sysUserDTO = sysUserFeignClient.save(sysUserSaveBO);
        return BeanUtil.copyProperties(sysUserDTO, SysUserVO.class);
    }

    public SysUserVO edit(SysUserEditParam sysUserEditParam) {
        SysUserEditBO sysUserEditBO = BeanUtil.copyProperties(sysUserEditParam, SysUserEditBO.class);
        SysUserDTO sysUserDTO = sysUserFeignClient.edit(sysUserEditBO);
        return BeanUtil.copyProperties(sysUserDTO, SysUserVO.class);
    }

    public Pageable<SysUserVO> page(SysUserQueryPageParam userQueryPageParam) {
        SysUserQueryPageBO sysUserQueryPageBO = BeanUtil.copyProperties(userQueryPageParam, SysUserQueryPageBO.class);
        Pageable<SysUserDTO> pageable = sysUserFeignClient.page(sysUserQueryPageBO);
        return PageableUtil.toPage(pageable, SysUserVO.class);
    }

    public Boolean changePassword(SysUserChangePasswordParam sysUserChangePasswordParam) {
        SysUserChangePasswordBO sysUserChangePasswordBO = BeanUtil.copyProperties(sysUserChangePasswordParam, SysUserChangePasswordBO.class);
        return sysUserFeignClient.changePassword(sysUserChangePasswordBO);
    }

    public Boolean deleteById(IdParam idParam) {
        return sysUserFeignClient.deleteById(idParam);
    }

    public LoginUserVO login(SysUserLoginParam sysUserLoginParam) {

        SysUserLoginBO sysUserLoginBO = BeanUtil.copyProperties(sysUserLoginParam, SysUserLoginBO.class);
        LoginUserDTO loginUserDTO = sysUserFeignClient.login(sysUserLoginBO);

        SysUserDTO loginUser = loginUserDTO.getUser();
        List<String> apisList = new ArrayList<>();
        this.getApisList(loginUserDTO.getPerms(), apisList);

        SimpleUser simpleUser = BeanUtil.copyProperties(loginUser, SimpleUser.class);
        simpleUser.setName(loginUser.getAccount());
        if (Objects.isNull(simpleUser.getRoleId()) && !loginUser.isSuper()) {
            throw BizException.build("user role is not assigned");
        }

        long expire = ExpireTime.ONE_DAY.getTime() / 2L;

        redisUtil.set(Constants.UID + ":" + loginUser.getUid(), simpleUser, expire);
        redisUtil.set(Constants.ROLE_PERMS + ":" + loginUser.getRoleId(), apisList, expire);

        Map<String, Object> map = new HashMap<>();
        map.put(Constants.UID, loginUser.getUid());
        map.put(Constants.USER_ID, loginUser.getId());
        String token = JwtUtil.createJwt(map, expire * 1000L);
        LoginUserVO loginUserVO = BeanUtil.copyProperties(loginUserDTO, LoginUserVO.class);
        loginUserVO.setToken(token);

        return loginUserVO;
    }

    private void getApisList(List<SysPermDTO> lst, List<String> apisList) {
        if (CollectionUtil.isEmpty(lst)) {
            return;
        }

        for (SysPermDTO node : lst) {
            String apis = node.getApis();
            if (apis != null && !apis.isEmpty()) {
                apisList.addAll(Arrays.asList(apis.split(",")));
            }

            if (node.getChildren() != null) {
                getApisList(node.getChildren(), apisList);
            } else return;
        }
    }

}
