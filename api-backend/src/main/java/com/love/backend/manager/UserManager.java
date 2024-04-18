package com.love.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.backend.model.param.UserEditParam;
import com.love.backend.model.param.UserQueryPageParam;
import com.love.backend.model.param.UserResetPasswordParam;
import com.love.backend.model.param.UserSaveParam;
import com.love.backend.model.vo.CustomerVO;
import com.love.backend.model.vo.UserDetailVO;
import com.love.backend.model.vo.UserVO;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageableUtil;
import com.love.order.bo.UserOrderStatBO;
import com.love.order.client.OrderFeignClient;
import com.love.order.dto.UserOrderStatDTO;
import com.love.user.client.UserFeignClient;
import com.love.user.sdk.bo.UserEditBO;
import com.love.user.sdk.bo.UserQueryPageBO;
import com.love.user.sdk.bo.UserResetPasswordBO;
import com.love.user.sdk.bo.UserSaveBO;
import com.love.user.sdk.dto.UserDTO;
import com.love.user.sdk.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserManager {

    private final UserFeignClient userFeignClient;
    private final OrderFeignClient orderFeignClient;

    public UserVO save(UserSaveParam userSaveParam) {
        UserSaveBO userSaveBO = BeanUtil.copyProperties(userSaveParam, UserSaveBO.class);
        userSaveBO.setAcceptTermsOfService(1);
        userSaveBO.setSubscribeToNewsletter(0);
        userSaveBO.setStatus(UserStatus.ACTIVATED.getStatus());
        UserDTO userDTO = userFeignClient.save(userSaveBO);
        return BeanUtil.copyProperties(userDTO, UserVO.class);
    }

    public UserVO edit(UserEditParam userEditParam) {
        UserEditBO userEditBO = BeanUtil.copyProperties(userEditParam, UserEditBO.class);
        UserDTO userDTO = userFeignClient.edit(userEditBO);
        return BeanUtil.copyProperties(userDTO, UserVO.class);
    }

    public UserDetailVO detail(IdParam idParam) {
        UserDTO userDTO = userFeignClient.detail(idParam);
        UserDetailVO detailVO = BeanUtil.copyProperties(userDTO, UserDetailVO.class);
        UserOrderStatBO userOrderStatBO = new UserOrderStatBO(idParam.getId());
        UserOrderStatDTO userOrderStatDTO = orderFeignClient.userStat(userOrderStatBO);
        detailVO.setOrders(userOrderStatDTO.getOrders());
        detailVO.setAmountSpent(userOrderStatDTO.getAmountSpent());
        return detailVO;
    }

    public Boolean deleteById(IdParam idParam) {
        Long orders = orderFeignClient.countByUserId(idParam);
        if (orders > 0) {
            throw BizException.build("The customer has orders, can't be deleted.");
        }

        return userFeignClient.deleteById(idParam);
    }

    public Pageable<CustomerVO> page(UserQueryPageParam userQueryPageParam) {
        UserQueryPageBO userQueryPageBO = BeanUtil.copyProperties(userQueryPageParam, UserQueryPageBO.class);
        Pageable<UserDTO> page = userFeignClient.page(userQueryPageBO);
        return PageableUtil.toPage(page, CustomerVO.class, (src, dst) -> {
            dst.setCustomerName(src.getFullName());
            UserOrderStatBO userOrderStatBO = new UserOrderStatBO(src.getId());
            UserOrderStatDTO userOrderStatDTO = orderFeignClient.userStat(userOrderStatBO);
            dst.setOrders(userOrderStatDTO.getOrders());
            dst.setAmountSpent(userOrderStatDTO.getAmountSpent());
        });
    }

    public Boolean resetPassword(UserResetPasswordParam userResetPasswordParam) {
        UserResetPasswordBO userResetPasswordBO = BeanUtil.copyProperties(userResetPasswordParam, UserResetPasswordBO.class);
        return userFeignClient.resetPassword(userResetPasswordBO);
    }
}
