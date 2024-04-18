package com.love.marketplace.model.dto;

import com.love.marketplace.model.param.ItemsParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class BoltOrderTokenDTO implements Serializable {
    private String orderToken;
    private Boolean isGuest;
    private String orderNo;
    private Long userId;
    private List<ItemsParam> itemsParams;
}
