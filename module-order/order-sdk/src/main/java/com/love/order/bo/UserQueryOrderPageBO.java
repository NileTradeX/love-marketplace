package com.love.order.bo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserQueryOrderPageBO implements Serializable {
    @Builder.Default
    private int pageNum = 0;
    @Builder.Default
    private int pageSize = 10;
    private Long userId;
    private Integer status;
}
