package com.love.goods.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGoodsSalesVolumeBO implements Serializable {
    private Long id;
    private Integer qty;
}
