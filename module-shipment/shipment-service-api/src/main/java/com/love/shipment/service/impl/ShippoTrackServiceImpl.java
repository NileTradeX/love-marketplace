package com.love.shipment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.shipment.bo.QueryTracksBO;
import com.love.shipment.bo.ShippoTrackBO;
import com.love.shipment.dto.QueryTracksDTO;
import com.love.shipment.entity.ShippoTrack;
import com.love.shipment.mapper.ShippoTrackMapper;
import com.love.shipment.service.ShippoTrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShippoTrackServiceImpl extends ServiceImpl<ShippoTrackMapper, ShippoTrack> implements ShippoTrackService {

    @Override
    @Transactional
    public Boolean saveTrack(ShippoTrackBO shippoTrackBO) {
        ShippoTrack shippoTrack = BeanUtil.copyProperties(shippoTrackBO, ShippoTrack.class);
        return saveOrUpdate(shippoTrack);
    }

    @Override
    public QueryTracksDTO queryByTrackNo(QueryTracksBO queryTracksBO) {
        ShippoTrack shippoTrack = this.lambdaQuery().eq(ShippoTrack::getTrackingNo, queryTracksBO.getTrackingNo()).one();
        QueryTracksDTO queryTracksDTO=new QueryTracksDTO();
        if(Objects.nonNull(shippoTrack)){
            queryTracksDTO.setJson(shippoTrack.getTrackingInfo());
        }
        return queryTracksDTO;
    }
}
