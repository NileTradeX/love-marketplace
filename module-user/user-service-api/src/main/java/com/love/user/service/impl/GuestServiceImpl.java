package com.love.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.param.IdsParam;
import com.love.user.entity.Guest;
import com.love.user.mapper.GuestMapper;
import com.love.user.sdk.bo.GuestBO;
import com.love.user.sdk.dto.GuestDTO;
import com.love.user.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GuestServiceImpl extends ServiceImpl<GuestMapper, Guest> implements GuestService {

    @Override
    public GuestDTO saveOrUpdate(GuestBO guestBO) {
        Guest guest = BeanUtil.copyProperties(guestBO, Guest.class);
        this.lambdaQuery().eq(Guest::getEmail, guestBO.getEmail()).oneOpt().ifPresent(g -> guest.setId(g.getId()));
        this.saveOrUpdate(guest);
        return BeanUtil.copyProperties(guest, GuestDTO.class);
    }

    @Override
    public GuestDTO queryByEmail(GuestBO guestBO) {
        Guest guest = this.lambdaQuery().eq(Guest::getEmail, guestBO.getEmail()).one();
        return BeanUtil.copyProperties(guest, GuestDTO.class);
    }

    @Override
    public List<GuestDTO> queryByIdList(IdsParam idsParam) {
        return BeanUtil.copyToList(this.lambdaQuery().in(Guest::getId, idsParam.getIdList()).list(), GuestDTO.class);
    }
}
