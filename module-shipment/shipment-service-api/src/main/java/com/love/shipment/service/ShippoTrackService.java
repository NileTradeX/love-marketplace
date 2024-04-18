package com.love.shipment.service;

import com.love.shipment.bo.QueryTracksBO;
import com.love.shipment.bo.ShippoTrackBO;
import com.love.shipment.dto.QueryTracksDTO;

public interface ShippoTrackService {

    Boolean saveTrack(ShippoTrackBO shippoTrackBO);

    QueryTracksDTO queryByTrackNo(QueryTracksBO queryTracksBO);
}
